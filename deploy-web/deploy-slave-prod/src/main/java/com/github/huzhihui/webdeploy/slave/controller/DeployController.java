/**
 * otoc.com Inc.
 * Copyright (c) 2016-2020 All Rights Reserved.
 */
package com.github.huzhihui.webdeploy.slave.controller;

import com.github.huzhihui.webdeploy.common.dto.SlaveDeployDto;
import com.github.huzhihui.webdeploy.common.dto.SlaveDeployResultDto;
import com.github.huzhihui.webdeploy.common.enums.DeployHistoryEnums;
import com.github.huzhihui.webdeploy.common.utils.JackSonUtils;
import com.github.huzhihui.webdeploy.common.utils.ResponseMessage;
import com.github.huzhihui.webdeploy.slave.config.WebPublishProperties;
import com.github.huzhihui.webdeploy.slave.utils.SignCheckUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;

/**
 * @author huzhihui
 * @version $ v 0.1 2020/8/10 17:32 huzhihui Exp $$
 */
@Slf4j
@Controller
@RequestMapping(value = "deploy")
public class DeployController {

    @Autowired
    private WebPublishProperties webPublishProperties;

    /**
     * 发布
     * @return
     */
    @RequestMapping(value = "add")
    @ResponseBody
    public ResponseMessage add(HttpServletRequest request,
                               @RequestParam("file") MultipartFile file,
                               @RequestParam("timeStamp") String timeStamp,
                               @RequestParam("terminalNum") String terminalNum,
                               @RequestParam("sign") String sign,
                               @RequestParam("jsonStr") String jsonStr){
        boolean check = SignCheckUtils.checkSign(timeStamp,terminalNum,sign,webPublishProperties.getTerminalNum(),webPublishProperties.getSignKey());
        if(!check){
            return ResponseMessage.failure("签名验证失败");
        }
        SlaveDeployDto slaveDeployDto = JackSonUtils.jsonStrToObject(jsonStr,SlaveDeployDto.class);
        // 文件保存到指定位置
        String suffix = this.getFileSuffix(file.getOriginalFilename());
        String tempName = UUID.randomUUID().toString();
        String fileName = tempName+suffix;
        String filePath = webPublishProperties.getTempFolder();
        File dir = new File(filePath);
        if (!dir.exists() && dir.isDirectory()) {
            dir.mkdirs();
        }
        File dest = new File(filePath + fileName);
        SlaveDeployResultDto result = null;
        try{
            file.transferTo(dest);
            result = this.executeShell(slaveDeployDto.getDeployFolder(),suffix,tempName,fileName,webPublishProperties.getShellFolder(),webPublishProperties.getShellFileName(),slaveDeployDto.getRootFolder(),webPublishProperties.getTempFolder(),slaveDeployDto.getPackageFolder());
        }catch (Exception ex){
            result = new SlaveDeployResultDto();
            result.setResult(false);
            result.setOperLog("服务调用异常");
        }
        return ResponseMessage.success(result);
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

    private SlaveDeployResultDto executeShell(String projectName, String suffix, String tempName, String fileName, String shellFolder, String shellFileName, String deployWorkspace, String deploySourceWorkspace, String deployCheckFolder) throws IOException, InterruptedException {
        SlaveDeployResultDto slaveDeployResultDto = new SlaveDeployResultDto();
        slaveDeployResultDto.setResult(false);
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
        log.info("执行日志----->"+operLog);
        slaveDeployResultDto.setOperLog(operLog);
        runningStatus = process.waitFor();
        if (runningStatus == 0) {
            slaveDeployResultDto.setResult(true);
        }
        return slaveDeployResultDto;
    }
}
