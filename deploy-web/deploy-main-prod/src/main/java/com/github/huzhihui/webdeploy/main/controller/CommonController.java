/**
 * otoc.com Inc.
 * Copyright (c) 2016-2020 All Rights Reserved.
 */
package com.github.huzhihui.webdeploy.main.controller;

import com.github.huzhihui.webdeploy.common.utils.ResponseMessage;
import com.github.huzhihui.webdeploy.main.interceptor.permission.Permission;
import com.github.huzhihui.webdeploy.main.utils.SessionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author huzhihui
 * @version $ v 0.1 2020/7/17 17:45 huzhihui Exp $$
 */
@Controller
public class CommonController {

    @RequestMapping(value = "error/500")
    public String error_500(){
        return "/error/500";
    }

    @RequestMapping(value = "common/template")
    public String template(){
        return "/common/template";
    }

    /**
     * 获取登陆用户信息
     * @return
     */
    @Permission
    @RequestMapping(value = "userInfo")
    @ResponseBody
    public ResponseMessage userInfo(){
        return ResponseMessage.success(SessionUtils.getUserInfo());
    }


}
