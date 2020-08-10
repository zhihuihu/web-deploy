/**
 * otoc.com Inc.
 * Copyright (c) 2016-2020 All Rights Reserved.
 */
package com.github.huzhihui.webdeploy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.github.huzhihui.webdeploy.common.utils.IdWorkerUtils;
import com.github.huzhihui.webdeploy.dao.DeployHistoryMapper;
import com.github.huzhihui.webdeploy.entity.DeployHistory;
import com.github.huzhihui.webdeploy.service.inter.DeployHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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

    @Override
    public List<DeployHistory> page(String endpointId, String projectId) {
        LambdaQueryWrapper<DeployHistory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotEmpty(endpointId),DeployHistory::getEndpointId,endpointId);
        queryWrapper.eq(StringUtils.isNotEmpty(projectId),DeployHistory::getProjectId,projectId);
        return deployHistoryMapper.selectList(queryWrapper);
    }
}
