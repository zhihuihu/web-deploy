/**
 * otoc.com Inc.
 * Copyright (c) 2016-2020 All Rights Reserved.
 */
package com.github.huzhihui.webdeploy.main.controller;

import com.github.huzhihui.webdeploy.common.constant.ConstantKey;
import com.github.huzhihui.webdeploy.common.utils.ResponseMessage;
import com.github.huzhihui.webdeploy.entity.Endpoint;
import com.github.huzhihui.webdeploy.main.dto.EndpointSelectResultDto;
import com.github.huzhihui.webdeploy.main.handler.EndpointHandler;
import com.github.huzhihui.webdeploy.main.interceptor.permission.Permission;
import com.github.huzhihui.webdeploy.service.inter.EndpointService;
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
 * @author huzhihui
 * @version $ v 0.1 2020/8/7 11:51 huzhihui Exp $$
 */
@Controller
@RequestMapping(value = "endpoint")
public class EndpointController {

    @Autowired
    private EndpointService endpointService;

    @Permission
    @RequestMapping(value = "index")
    public String index(Model model){
        model.addAttribute("page","endpoint/index");
        return "endpoint/index";
    }

    @Permission
    @RequestMapping(value = "p_add")
    public String pAdd(){
        return "endpoint/add";
    }

    @Permission
    @RequestMapping(value = "p_modify")
    public String pModify(Model model,String id){
        Endpoint endpoint = endpointService.getById(id);
        model.addAttribute("endpoint",endpoint);
        return "endpoint/modify";
    }

    @Permission
    @RequestMapping(value = "page")
    @ResponseBody
    public ResponseMessage page(@RequestParam(value = "pageNum") int pageNum,
                                @RequestParam(value = "pageSize") int pageSize,
                                String search){
        PageHelper.startPage(pageNum, pageSize);
        List<Endpoint> endpoints = endpointService.page(null,search);
        PageInfo pageInfo = new PageInfo(endpoints);
        return ResponseMessage.success(pageInfo);
    }

    @Permission
    @RequestMapping(value = "add")
    @ResponseBody
    public ResponseMessage add(@RequestBody Endpoint endpoint){
        endpointService.add(endpoint);
        return ResponseMessage.success();
    }

    @Permission
    @RequestMapping(value = "modify")
    @ResponseBody
    public ResponseMessage modify(@RequestBody Endpoint endpoint){
        Endpoint endpointCheck = endpointService.getById(endpoint.getId());
        if(endpointCheck.getTerminalNum().equals(ConstantKey.master_terminal_num)){
            return ResponseMessage.failure("Master节点不允许修改");
        }
        endpointService.modifyById(endpoint);
        return ResponseMessage.success();
    }
}
