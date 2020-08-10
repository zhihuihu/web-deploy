/**
 * otoc.com Inc.
 * Copyright (c) 2016-2020 All Rights Reserved.
 */
package com.github.huzhihui.webdeploy.main.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.github.huzhihui.webdeploy.common.constant.ConstantKey;
import com.github.huzhihui.webdeploy.common.enums.EndpointEnums;
import com.github.huzhihui.webdeploy.common.utils.AssertUtils;
import com.github.huzhihui.webdeploy.common.utils.MD5Utils;
import com.github.huzhihui.webdeploy.common.utils.ResponseMessage;
import com.github.huzhihui.webdeploy.entity.Endpoint;
import com.github.huzhihui.webdeploy.entity.User;
import com.github.huzhihui.webdeploy.main.dto.UserInfo;
import com.github.huzhihui.webdeploy.main.utils.SessionUtils;
import com.github.huzhihui.webdeploy.service.inter.EndpointService;
import com.github.huzhihui.webdeploy.service.inter.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author huzhihui
 * @version $ v 0.1 2020/7/17 17:45 huzhihui Exp $$
 */
@Slf4j
@Controller
@RequestMapping(value = "login")
public class LoginController {

    @Autowired
    private UserService userService;
    @Autowired
    private EndpointService endpointService;

    @RequestMapping(value = {"","/"})
    public String login(){
        return "login";
    }

    /**
     * 登陆验证方法
     * @param userName
     * @param password
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "verification")
    @ResponseBody
    public ResponseMessage verification(String userName,String password) throws Exception{
        AssertUtils.isNotEmpty(log,userName,"用户名为空");
        AssertUtils.isNotEmpty(log,password,"密码为空");
        User user = userService.getByUserName(userName);
        AssertUtils.isNotNull(log,user,"用户名不存在");
        password = MD5Utils.MD5(password+user.getSalt()).toUpperCase();
        AssertUtils.isTrue(log,password.equals(user.getPassword()),"密码错误");
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName(user.getUserName());
        userInfo.setUserCname(user.getUserCname());
        // 设置默认端点
        List<Endpoint> endpoints = endpointService.page(EndpointEnums.USE_FLAG.ENABLE.getValue(),null);
        if(StringUtils.isEmpty(userInfo.getEndpointId())){
            Endpoint endpointL = endpoints.stream().filter(endpoint -> endpoint.getTerminalNum().equals(ConstantKey.master_terminal_num)).findFirst().get();
            userInfo.setEndpointId(endpointL.getId());
            userInfo.setEndpointName(endpointL.getName());
        }
        SessionUtils.setUserInfo(userInfo);
        return ResponseMessage.success();
    }
}
