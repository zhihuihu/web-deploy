/**
 * otoc.com Inc.
 * Copyright (c) 2016-2020 All Rights Reserved.
 */
package com.github.huzhihui.webdeploy.controller;

import com.github.huzhihui.webdeploy.entity.User;
import com.github.huzhihui.webdeploy.service.inter.UserService;
import com.github.huzhihui.webdeploy.utils.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author huzhihui
 * @version $ v 0.1 2020/7/21 16:03 huzhihui Exp $$
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "add")
    @ResponseBody
    public ResponseMessage add(@RequestBody User user){
        userService.add(user);
        return ResponseMessage.success();
    }
}
