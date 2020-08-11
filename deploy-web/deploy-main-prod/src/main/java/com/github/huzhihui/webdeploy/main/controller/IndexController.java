/**
 * otoc.com Inc.
 * Copyright (c) 2016-2020 All Rights Reserved.
 */
package com.github.huzhihui.webdeploy.main.controller;

import com.github.huzhihui.webdeploy.common.constant.ConstantKey;
import com.github.huzhihui.webdeploy.common.enums.EndpointEnums;
import com.github.huzhihui.webdeploy.common.enums.ProjectEnums;
import com.github.huzhihui.webdeploy.common.utils.AssertUtils;
import com.github.huzhihui.webdeploy.common.utils.ResponseMessage;
import com.github.huzhihui.webdeploy.common.utils.SignUtils;
import com.github.huzhihui.webdeploy.entity.DeployHistory;
import com.github.huzhihui.webdeploy.entity.Endpoint;
import com.github.huzhihui.webdeploy.entity.Project;
import com.github.huzhihui.webdeploy.main.dto.EndpointSelectResultDto;
import com.github.huzhihui.webdeploy.main.dto.EndpointStatusDto;
import com.github.huzhihui.webdeploy.main.dto.UserInfo;
import com.github.huzhihui.webdeploy.main.handler.EndpointHandler;
import com.github.huzhihui.webdeploy.main.interceptor.permission.Permission;
import com.github.huzhihui.webdeploy.main.utils.SessionUtils;
import com.github.huzhihui.webdeploy.service.inter.DeployHistoryService;
import com.github.huzhihui.webdeploy.service.inter.EndpointService;
import com.github.huzhihui.webdeploy.service.inter.ProjectService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author huzhihui
 * @version $ v 0.1 2020/8/6 16:51 huzhihui Exp $$
 */
@Slf4j
@Controller
public class IndexController {

    @Autowired
    private ProjectService projectService;
    @Autowired
    private EndpointService endpointService;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private DeployHistoryService deployHistoryService;

    /**
     * 首页
     * @return
     */
    @Permission
    @RequestMapping(value = {"index","/"})
    public String index(Model model){
        model.addAttribute("page","index");
        return "index";
    }

    @Permission
    @RequestMapping(value = "index/endpointStatus")
    @ResponseBody
    public ResponseMessage endpointStatus(){
        List<EndpointStatusDto> endpointStatusDtos = new ArrayList<>();
        List<Endpoint> endpoints = endpointService.page(EndpointEnums.USE_FLAG.ENABLE.getValue(),null);
        endpoints.stream().forEach(endpoint -> {
            EndpointStatusDto endpointStatusDto = new EndpointStatusDto();
            endpointStatusDto.setId(endpoint.getId());
            endpointStatusDto.setName(endpoint.getName());
            endpointStatusDto.setHost(endpoint.getHost());
            endpointStatusDto.setPort(endpoint.getPort());
            endpointStatusDto.setCreateTime(endpoint.getCreateTime());
            endpointStatusDto.setRun(true);
            try{
                String timeStamp = System.currentTimeMillis() + "";
                String terminalNum = endpoint.getTerminalNum();
                String sign = SignUtils.sign(new HashMap<String,String>(){{
                    put("timeStamp",timeStamp);
                    put("terminalNum",terminalNum);
                }},endpoint.getSignKey(),SignUtils.HMACSHA256);
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
                params.add("timeStamp", timeStamp);
                params.add("terminalNum", terminalNum);
                params.add("sign", sign);
                HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(params, headers);
                ResponseEntity<String> response = restTemplate.exchange(endpoint.getHost()+":"+endpoint.getPort()+ ConstantKey.ENDPOINT_CHECK_URL, HttpMethod.POST, requestEntity, String.class);
                log.info("服务器根节点状态：" + response.getBody());
            }catch (Exception ex){
                endpointStatusDto.setRun(false);
            }
            if(endpoint.getTerminalNum().equals(ConstantKey.master_terminal_num)){
                endpointStatusDto.setRun(true);
            }
            endpointStatusDtos.add(endpointStatusDto);
        });
        return ResponseMessage.success(endpointStatusDtos);
    }

    /**
     * 最近发布情况
     * @return
     */
    @Permission
    @RequestMapping(value = "index/latestDeploy")
    @ResponseBody
    public ResponseMessage latestDeploy(){
        PageHelper.startPage(1,10);
        PageHelper.orderBy("create_time desc");
        List<DeployHistory> deployHistories = deployHistoryService.page(null,null);
        PageInfo pageInfo = new PageInfo(deployHistories);
        return ResponseMessage.success(pageInfo);
    }

    /**
     * 终端变更
     * @param endpointId
     * @return
     */
    @Permission
    @RequestMapping(value = "index/changeEndpoint")
    @ResponseBody
    public ResponseMessage changeEndpoint(String endpointId){
        Endpoint endpoint = endpointService.getById(endpointId);
        AssertUtils.isNotNull(log,endpoint,"终端不存在");
        UserInfo userInfo = SessionUtils.getUserInfo();
        userInfo.setEndpointId(endpoint.getId());
        userInfo.setEndpointName(endpoint.getName());
        SessionUtils.setUserInfo(userInfo);
        return ResponseMessage.success();
    }

}
