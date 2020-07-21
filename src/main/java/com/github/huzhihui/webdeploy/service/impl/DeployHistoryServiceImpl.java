/**
 * otoc.com Inc.
 * Copyright (c) 2016-2020 All Rights Reserved.
 */
package com.github.huzhihui.webdeploy.service.impl;

import com.github.huzhihui.webdeploy.dao.DeployHistoryMapper;
import com.github.huzhihui.webdeploy.entity.DeployHistory;
import com.github.huzhihui.webdeploy.service.inter.DeployHistoryService;
import com.github.huzhihui.webdeploy.utils.IdWorkerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author huzhihui
 * @version $ v 0.1 2020/7/17 17:41 huzhihui Exp $$
 */
@Service
public class DeployHistoryServiceImpl implements DeployHistoryService {

    @Autowired
    private DeployHistoryMapper deployHistoryMapper;

    @Override
    public int add(DeployHistory deployHistory) {
        deployHistory.setId(IdWorkerUtils.getId());
        deployHistory.setCreateTime(new Date());
        return deployHistoryMapper.insert(deployHistory);
    }

    @Override
    public int modifyById(DeployHistory deployHistory) {
        return deployHistoryMapper.updateById(deployHistory);
    }

    @Override
    public DeployHistory getById(String id) {
        return deployHistoryMapper.selectById(id);
    }
}
