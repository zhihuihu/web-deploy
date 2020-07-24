/**
 * otoc.com Inc.
 * Copyright (c) 2016-2020 All Rights Reserved.
 */
package com.github.huzhihui.webdeploy.controller;

import com.github.huzhihui.webdeploy.entity.DeployFile;
import com.github.huzhihui.webdeploy.service.inter.DeployFileService;
import com.github.huzhihui.webdeploy.utils.FileUtils;
import com.github.huzhihui.webdeploy.utils.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author huzhihui
 * @version $ v 0.1 2020/7/24 9:56 huzhihui Exp $$
 */
@Controller
@RequestMapping(value = "deploy/file")
public class DeployFileController {

    @Autowired
    private DeployFileService deployFileService;

    @RequestMapping(value = "uploadFile")
    @ResponseBody
    public ResponseMessage uploadFile(@RequestParam("file") MultipartFile file) throws Exception{
        DeployFile deployFile = new DeployFile();
        deployFile.setName(file.getOriginalFilename());
        deployFile.setSource(file.getBytes());
        deployFileService.add(deployFile);
        return ResponseMessage.success(deployFile);
    }

    @RequestMapping(value = "exFile")
    @ResponseBody
    public ResponseMessage exFile(String id) throws Exception{
        DeployFile deployFile = deployFileService.getById(id);
        FileUtils.getFileByBytes(deployFile.getSource(),"D:/",deployFile.getName());
        return ResponseMessage.success(deployFile);
    }
}
