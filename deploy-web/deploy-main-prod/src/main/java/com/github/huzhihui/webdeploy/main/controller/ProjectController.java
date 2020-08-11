/**
 * otoc.com Inc.
 * Copyright (c) 2016-2020 All Rights Reserved.
 */
package com.github.huzhihui.webdeploy.main.controller;

import com.github.huzhihui.webdeploy.common.utils.ResponseMessage;
import com.github.huzhihui.webdeploy.entity.Project;
import com.github.huzhihui.webdeploy.main.dto.EndpointSelectResultDto;
import com.github.huzhihui.webdeploy.main.dto.UserInfo;
import com.github.huzhihui.webdeploy.main.handler.EndpointHandler;
import com.github.huzhihui.webdeploy.main.interceptor.permission.Permission;
import com.github.huzhihui.webdeploy.main.utils.SessionUtils;
import com.github.huzhihui.webdeploy.service.inter.ProjectService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 项目控制器
 * @author huzhihui
 * @version $ v 0.1 2020/7/29 14:06 huzhihui Exp $$
 */
@Controller
@RequestMapping(value = "project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Permission
    @RequestMapping(value = "index")
    public String index(Model model){
        model.addAttribute("page","project/index");
        return "project/index";
    }

    @Permission
    @RequestMapping(value = "p_add")
    public String pAdd(){
        return "project/add";
    }

    @Permission
    @RequestMapping(value = "p_modify")
    public String pModify(Model model,String id){
        Project project = projectService.getById(id);
        model.addAttribute("project",project);
        return "project/modify";
    }

    @Permission
    @RequestMapping(value = "page")
    @ResponseBody
    public ResponseMessage page(@RequestParam(value = "pageNum") int pageNum,
                                @RequestParam(value = "pageSize") int pageSize,
                                String search){
        UserInfo userInfo = SessionUtils.getUserInfo();
        PageHelper.startPage(pageNum, pageSize);
        List<Project> projects = projectService.page(userInfo.getEndpointId(),null,search);
        PageInfo pageInfo = new PageInfo(projects);
        return ResponseMessage.success(pageInfo);
    }

    @Permission
    @RequestMapping(value = "add")
    @ResponseBody
    public ResponseMessage add(@RequestBody Project project){
        UserInfo userInfo = SessionUtils.getUserInfo();
        project.setEndpointId(userInfo.getEndpointId());
        projectService.add(project);
        return ResponseMessage.success();
    }

    @Permission
    @RequestMapping(value = "modify")
    @ResponseBody
    public ResponseMessage modify(@RequestBody Project project){
        project.setEndpointId(null);
        projectService.modifyById(project);
        return ResponseMessage.success();
    }

}
