/**
 * otoc.com Inc.
 * Copyright (c) 2016-2020 All Rights Reserved.
 */
package com.github.huzhihui.webdeploy.service.impl;

import com.github.huzhihui.webdeploy.dao.DeployFileMapper;
import com.github.huzhihui.webdeploy.entity.DeployFile;
import com.github.huzhihui.webdeploy.service.inter.DeployFileService;
import com.github.huzhihui.webdeploy.utils.IdWorkerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author huzhihui
 * @version $ v 0.1 2020/7/24 9:59 huzhihui Exp $$
 */
@Service
public class DeployFileServiceImpl implements DeployFileService {

    @Autowired
    DeployFileMapper deployFileMapper;

    @Override
    public int add(DeployFile deployFile) {
        deployFile.setId(IdWorkerUtils.getId());
        deployFile.setCreateTime(new Date());
        return deployFileMapper.insert(deployFile);
    }

    @Override
    public DeployFile getById(String id) {
        return deployFileMapper.selectById(id);
    }
}
