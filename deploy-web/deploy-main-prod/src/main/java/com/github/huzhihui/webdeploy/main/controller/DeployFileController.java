/**
 * otoc.com Inc.
 * Copyright (c) 2016-2020 All Rights Reserved.
 */
package com.github.huzhihui.webdeploy.main.controller;

import com.github.huzhihui.webdeploy.common.utils.FileUtils;
import com.github.huzhihui.webdeploy.common.utils.ResponseMessage;
import com.github.huzhihui.webdeploy.entity.DeployFile;
import com.github.huzhihui.webdeploy.service.inter.DeployFileService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @author huzhihui
 * @version $ v 0.1 2020/7/24 9:56 huzhihui Exp $$
 */
@Controller
@RequestMapping(value = "deploy/file")
public class DeployFileController {

    @Autowired
    private DeployFileService deployFileService;

    @RequestMapping(value = "download")
    public void download(HttpServletResponse response, String id) throws Exception{
        DeployFile deployFile = deployFileService.getById(id);
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition", "attachment;fileName="+deployFile.getName());
        ServletOutputStream out = response.getOutputStream();
        InputStream input = new ByteArrayInputStream(deployFile.getSource());
        IOUtils.copy(input, out);
    }

}
