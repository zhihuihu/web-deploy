/**
 * otoc.com Inc.
 * Copyright (c) 2016-2020 All Rights Reserved.
 */
package com.github.huzhihui.webdeploy.main.controller;

import com.github.huzhihui.webdeploy.common.utils.AssertUtils;
import com.github.huzhihui.webdeploy.common.utils.ResponseMessage;
import com.github.huzhihui.webdeploy.entity.User;
import com.github.huzhihui.webdeploy.main.dto.EndpointSelectResultDto;
import com.github.huzhihui.webdeploy.main.handler.EndpointHandler;
import com.github.huzhihui.webdeploy.main.interceptor.permission.Permission;
import com.github.huzhihui.webdeploy.service.inter.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author huzhihui
 * @version $ v 0.1 2020/7/21 16:03 huzhihui Exp $$
 */
@Slf4j
@Controller
@RequestMapping(value = "user")
public class UserController {

    @Autowired
    private UserService userService;

    @Permission
    @RequestMapping(value = "index")
    public String index(Model model){
        model.addAttribute("page","user/index");
        return "user/index";
    }

    @Permission
    @RequestMapping(value = "p_add")
    public String pAdd(){
        return "user/add";
    }

    @Permission
    @RequestMapping(value = "p_modify")
    public String pModify(Model model,String id){
        User user = userService.getById(id);
        model.addAttribute("user",user);
        return "user/modify";
    }

    @Permission
    @RequestMapping(value = "page")
    @ResponseBody
    public ResponseMessage page(@RequestParam(value = "pageNum") int pageNum,
                                @RequestParam(value = "pageSize") int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<User> users = userService.page(null,null);
        PageInfo pageInfo = new PageInfo(users);
        return ResponseMessage.success(pageInfo);
    }

    @Permission
    @RequestMapping(value = "add")
    @ResponseBody
    public ResponseMessage add(@RequestBody User user){
        AssertUtils.isNotEmpty(log,user.getUserName(),"用户名为空");
        AssertUtils.isNotEmpty(log,user.getUserCname(),"用户名中文名为空");
        AssertUtils.isNotEmpty(log,user.getPassword(),"密码为空");
        userService.add(user);
        return ResponseMessage.success();
    }

    @Permission
    @RequestMapping(value = "modify")
    @ResponseBody
    public ResponseMessage modify(@RequestBody User user){
        userService.modifyById(user);
        return ResponseMessage.success();
    }


}
