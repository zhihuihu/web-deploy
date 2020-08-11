/**
 * otoc.com Inc.
 * Copyright (c) 2016-2020 All Rights Reserved.
 */
package com.github.huzhihui.webdeploy.main.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.huzhihui.webdeploy.common.constant.ConstantKey;
import com.github.huzhihui.webdeploy.common.dto.SlaveDeployDto;
import com.github.huzhihui.webdeploy.common.dto.SlaveDeployResultDto;
import com.github.huzhihui.webdeploy.common.enums.DeployHistoryEnums;
import com.github.huzhihui.webdeploy.common.enums.ProjectEnums;
import com.github.huzhihui.webdeploy.common.utils.*;
import com.github.huzhihui.webdeploy.entity.DeployFile;
import com.github.huzhihui.webdeploy.entity.DeployHistory;
import com.github.huzhihui.webdeploy.entity.Endpoint;
import com.github.huzhihui.webdeploy.entity.Project;
import com.github.huzhihui.webdeploy.main.config.WebPublishProperties;
import com.github.huzhihui.webdeploy.main.dto.UserInfo;
import com.github.huzhihui.webdeploy.main.interceptor.permission.Permission;
import com.github.huzhihui.webdeploy.main.utils.SessionUtils;
import com.github.huzhihui.webdeploy.service.inter.DeployFileService;
import com.github.huzhihui.webdeploy.service.inter.DeployHistoryService;
import com.github.huzhihui.webdeploy.service.inter.EndpointService;
import com.github.huzhihui.webdeploy.service.inter.ProjectService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * 项目发布控制器
 * @author huzhihui
 * @version $ v 0.1 2020/8/10 11:12 huzhihui Exp $$
 */
@Slf4j
@Controller
@RequestMapping(value = "deploy")
public class DeployController {

    @Autowired
    private DeployHistoryService deployHistoryService;
    @Autowired
    private DeployFileService deployFileService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private WebPublishProperties webPublishProperties;
    @Autowired
    private EndpointService endpointService;
    @Autowired
    private RestTemplate restTemplate;

    @Permission
    @RequestMapping(value = "index")
    public String index(Model model){
        UserInfo userInfo = SessionUtils.getUserInfo();
        List<Project> projects = projectService.page(userInfo.getEndpointId(),ProjectEnums.USE_FLAG.ENABLE.getValue(),null);
        model.addAttribute("projects",projects);
        model.addAttribute("page","deploy/index");
        return "deploy/index";
    }

    @Permission
    @RequestMapping(value = "p_add")
    public String pAdd(Model model){
        UserInfo userInfo = SessionUtils.getUserInfo();
        List<Project> projects = projectService.page(userInfo.getEndpointId(),ProjectEnums.USE_FLAG.ENABLE.getValue(),null);
        model.addAttribute("projects",projects);
        return "deploy/add";
    }

    @Permission
    @RequestMapping(value = "page")
    @ResponseBody
    public ResponseMessage page(@RequestParam(value = "pageNum") int pageNum,
                                @RequestParam(value = "pageSize") int pageSize,
                                String search){
        UserInfo userInfo = SessionUtils.getUserInfo();
        PageHelper.startPage(pageNum, pageSize);
        PageHelper.orderBy("create_time desc");
        List<DeployHistory> deployHistories = deployHistoryService.page(userInfo.getEndpointId(),null);
        PageInfo pageInfo = new PageInfo(deployHistories);
        return ResponseMessage.success(pageInfo);
    }

    @Permission
    @RequestMapping(value = "add")
    @ResponseBody
    public ResponseMessage add(@RequestParam("file") MultipartFile file,
                               @RequestParam(value = "projectId") String projectId,
                               @RequestParam(value = "describe") String describe){
        UserInfo userInfo = SessionUtils.getUserInfo();
        Project project = projectService.getById(projectId);
        AssertUtils.isNotNull(log,project,"该节点不存在该项目");
        AssertUtils.isTrue(log,project.getEndpointId().equals(userInfo.getEndpointId()),"该节点不存在该项目");
        Endpoint endpoint = endpointService.getById(userInfo.getEndpointId());

        // 判断是否是主节点
        boolean masterEndpoint = endpoint.getTerminalNum().equals(ConstantKey.master_terminal_num);
        // 后缀名
        String suffix = this.getFileSuffix(file.getOriginalFilename());
        AssertUtils.isNotEmpty(log,suffix,"文件类型错误");

        // 文件保存到指定位置
        String tempName = UUID.randomUUID().toString();
        String fileName = tempName+suffix;
        String filePath = webPublishProperties.getTempFolder();
        File dir = new File(filePath);
        if (!dir.exists() && dir.isDirectory()) {
            dir.mkdirs();
        }
        File dest = new File(filePath + fileName);

        // 文件保存
        DeployFile deployFile = new DeployFile();
        deployFile.setName(fileName);
        try{
            deployFile.setSource(file.getBytes());
        }catch (Exception ex){
            return ResponseMessage.failure("文件类型错误");
        }
        deployFileService.add(deployFile);
        // 发布
        DeployHistory deployHistory = new DeployHistory();
        deployHistory.setEndpointId(userInfo.getEndpointId());
        deployHistory.setEndpointName(userInfo.getEndpointName());
        deployHistory.setProjectId(projectId);
        deployHistory.setProjectName(project.getName());
        deployHistory.setDeployFileId(deployFile.getId());
        deployHistory.setDescribe(describe);
        deployHistory.setHisFileName(fileName);
        deployHistory.setStatus(DeployHistoryEnums.STATUS.FAIL.getValue());
        deployHistory.setOperUserCname(userInfo.getUserCname());
        deployHistory.setOperLog("");

        try{
            // 主节点发布
            if(masterEndpoint){
                file.transferTo(dest);
                this.executeShell(project.getDeployFolder(),suffix,tempName,fileName,webPublishProperties.getShellFolder(),webPublishProperties.getShellFileName(),project.getRootFolder(),webPublishProperties.getTempFolder(),project.getPackageFolder(),deployHistory);
            }else{
                this.deploySlaveEndpoint(project, endpoint, suffix, tempName, fileName, deployFile, deployHistory);
            }
            log.debug("执行完成");
        }catch (Exception ex){
            log.error("发布失败",ex);
        }
        deployHistoryService.add(deployHistory);
        if(deployHistory.getStatus().equals(DeployHistoryEnums.STATUS.FAIL.getValue())){
            return ResponseMessage.failure("发布失败");
        }
        return ResponseMessage.success();
    }

    /**
     * 发布其他节点
     * @param project
     * @param endpoint
     * @param suffix
     * @param tempName
     * @param fileName
     * @param deployFile
     * @param deployHistory
     * @throws Exception
     */
    private void deploySlaveEndpoint(Project project, Endpoint endpoint, String suffix, String tempName, String fileName, DeployFile deployFile, DeployHistory deployHistory) throws Exception {
        String timeStamp = System.currentTimeMillis() + "";
        String terminalNum = endpoint.getTerminalNum();
        String sign = SignUtils.sign(new HashMap<String,String>(){{
            put("timeStamp",timeStamp);
            put("terminalNum",terminalNum);
        }},endpoint.getSignKey(),SignUtils.HMACSHA256);
        // 其他节点发布
        FileSystemResource resource = new FileSystemResource(FileUtils.getTempFileByBytes(deployFile.getSource(),tempName,suffix));
        // 然后所有参数要封装到MultiValueMap里面
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.add("file", resource);
        param.add("timeStamp", timeStamp);
        param.add("terminalNum", terminalNum);
        param.add("sign", sign);
        SlaveDeployDto slaveDeployDto = new SlaveDeployDto();
        slaveDeployDto.setProjectName(project.getName());
        slaveDeployDto.setRootFolder(project.getRootFolder());
        slaveDeployDto.setDeployFolder(project.getDeployFolder());
        slaveDeployDto.setPackageFolder(project.getPackageFolder());
        slaveDeployDto.setFileName(fileName);
        param.add("jsonStr", JackSonUtils.objectToJsonStr(slaveDeployDto));
        // 调用接口即可
        ResponseEntity<String> response = restTemplate.postForEntity(endpoint.getHost()+":"+endpoint.getPort()+ ConstantKey.ENDPOINT_DEPLOY_URL, param, String.class);
        JSONObject jsonObject = JSON.parseObject(response.getBody());
        if(jsonObject.getBoolean("success")){
            SlaveDeployResultDto slaveDeployResultDto = JSONObject.parseObject(jsonObject.getString("data"),SlaveDeployResultDto.class);
            deployHistory.setOperLog(slaveDeployResultDto.getOperLog());
            if(slaveDeployResultDto.getResult()){
                deployHistory.setStatus(DeployHistoryEnums.STATUS.SUCCESS.getValue());
            }else{
                deployHistory.setStatus(DeployHistoryEnums.STATUS.FAIL.getValue());
            }
        }else{
            deployHistory.setStatus(DeployHistoryEnums.STATUS.FAIL.getValue());
            deployHistory.setOperLog(jsonObject.getString("codeMessage"));
        }
    }

    /**
     * 版本回滚
     * @param id
     * @return
     */
    @Permission
    @RequestMapping(value = "rollback")
    @ResponseBody
    public ResponseMessage rollback(String id){
        UserInfo userInfo = SessionUtils.getUserInfo();
        Endpoint endpoint = endpointService.getById(userInfo.getEndpointId());
        DeployHistory deployHistoryCheck = deployHistoryService.getById(id);
        AssertUtils.isNotNull(log,deployHistoryCheck,"回滚版本不存在");
        AssertUtils.isTrue(log,deployHistoryCheck.getStatus().equals(DeployHistoryEnums.STATUS.SUCCESS.getValue()),"回滚版本不存在");
        AssertUtils.isTrue(log,userInfo.getEndpointId().equals(deployHistoryCheck.getEndpointId()),"终端不一致请刷新后再试");
        Project project = projectService.getById(deployHistoryCheck.getProjectId());
        DeployFile deployFile = deployFileService.getById(deployHistoryCheck.getDeployFileId());

        // 判断是否是主节点
        boolean masterEndpoint = endpoint.getTerminalNum().equals(ConstantKey.master_terminal_num);
        // 后缀名
        String suffix = this.getFileSuffix(deployHistoryCheck.getHisFileName());
        AssertUtils.isNotEmpty(log,suffix,"文件类型错误");

        // 文件保存到指定位置
        String tempName = UUID.randomUUID().toString();
        String fileName = tempName+suffix;
        String filePath = webPublishProperties.getTempFolder();

        // 发布
        DeployHistory deployHistory = new DeployHistory();
        deployHistory.setEndpointId(userInfo.getEndpointId());
        deployHistory.setEndpointName(userInfo.getEndpointName());
        deployHistory.setProjectId(deployHistoryCheck.getProjectId());
        deployHistory.setProjectName(project.getName());
        deployHistory.setHisFileName(fileName);
        deployHistory.setDescribe("版本回退-->"+deployHistoryCheck.getDescribe());
        deployHistory.setStatus(DeployHistoryEnums.STATUS.FAIL.getValue());
        deployHistory.setOperUserCname(userInfo.getUserCname());
        deployHistory.setOperLog("");

        try{
            // 主节点发布
            if(masterEndpoint){
                FileUtils.getFileByBytes(deployFile.getSource(),filePath,fileName);
                this.executeShell(project.getDeployFolder(),suffix,tempName,fileName,webPublishProperties.getShellFolder(),webPublishProperties.getShellFileName(),project.getRootFolder(),webPublishProperties.getTempFolder(),project.getPackageFolder(),deployHistory);
            }else{
                // 其他节点发布

            }
            log.debug("执行完成");
        }catch (Exception ex){
            log.error("发布失败",ex);
        }
        deployHistoryService.add(deployHistory);
        if(deployHistory.getStatus().equals(DeployHistoryEnums.STATUS.FAIL.getValue())){
            return ResponseMessage.failure("发布失败");
        }
        return ResponseMessage.success();
    }

    /**
     * 获取后缀名
     * @param originalFilename
     * @return
     */
    private String getFileSuffix(String originalFilename){
        if(originalFilename.endsWith(".zip")){
            return ".zip";
        }else if(originalFilename.endsWith(".rar")){
            return ".rar";
        }
        return null;
    }

    private void executeShell(String projectName, String suffix, String tempName, String fileName, String shellFolder, String shellFileName, String deployWorkspace, String deploySourceWorkspace, String deployCheckFolder, DeployHistory deployHistory) throws IOException, InterruptedException {
        // 发布项目名称
        String deployProjectName = projectName;
        // 发布的源文件名称
        String deploySourceName = fileName;
        // 发布的源文件类型
        String deploySourceType = suffix.substring(1);
        // 发布临时文件夹
        String deployTempFolder = tempName+"/";
        ProcessBuilder processBuilder = new ProcessBuilder("./" + shellFileName, deployProjectName,
                deploySourceName, deploySourceType, deployTempFolder, deployWorkspace,deploySourceWorkspace ,deployCheckFolder);
        processBuilder.directory(new File(shellFolder));
        int runningStatus = 0;
        String operLog = "";
        Process process = processBuilder.start();
        BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = "";
        while ((line = input.readLine()) != null) {
            operLog = operLog + line + "\n";
        }
        input.close();
        deployHistory.setOperLog(operLog);
        log.debug("执行日志----->"+operLog);
        runningStatus = process.waitFor();
        if (runningStatus == 0) {
            deployHistory.setStatus(DeployHistoryEnums.STATUS.SUCCESS.getValue());
        }
    }
}
