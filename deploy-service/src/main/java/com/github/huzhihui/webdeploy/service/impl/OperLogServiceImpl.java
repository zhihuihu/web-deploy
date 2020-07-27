/**
 * otoc.com Inc.
 * Copyright (c) 2016-2020 All Rights Reserved.
 */
package com.github.huzhihui.webdeploy.service.impl;

import com.github.huzhihui.webdeploy.common.utils.IdWorkerUtils;
import com.github.huzhihui.webdeploy.dao.OperLogMapper;
import com.github.huzhihui.webdeploy.entity.OperLog;
import com.github.huzhihui.webdeploy.service.inter.OperLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author huzhihui
 * @version $ v 0.1 2020/7/17 17:41 huzhihui Exp $$
 */
@Service
public class OperLogServiceImpl implements OperLogService {

    @Autowired
    private OperLogMapper operLogMapper;

    @Override
    public int add(OperLog operLog) {
        operLog.setId(IdWorkerUtils.getId());
        operLog.setCreateTime(new Date());
        return operLogMapper.insert(operLog);
    }
}
