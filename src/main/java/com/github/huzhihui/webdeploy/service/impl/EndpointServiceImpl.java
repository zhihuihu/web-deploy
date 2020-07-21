/**
 * otoc.com Inc.
 * Copyright (c) 2016-2020 All Rights Reserved.
 */
package com.github.huzhihui.webdeploy.service.impl;

import com.github.huzhihui.webdeploy.dao.EndpointMapper;
import com.github.huzhihui.webdeploy.entity.Endpoint;
import com.github.huzhihui.webdeploy.service.inter.EndpointService;
import com.github.huzhihui.webdeploy.utils.IdWorkerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author huzhihui
 * @version $ v 0.1 2020/7/21 15:25 huzhihui Exp $$
 */
@Service
public class EndpointServiceImpl implements EndpointService {

    @Autowired
    private EndpointMapper endpointMapper;

    @Override
    public int add(Endpoint endpoint) {
        endpoint.setId(IdWorkerUtils.getId());
        endpoint.setCreateTime(new Date());
        return endpointMapper.insert(endpoint);
    }

    @Override
    public int modifyById(Endpoint endpoint) {
        return endpointMapper.updateById(endpoint);
    }

    @Override
    public Endpoint getById(String id) {
        return endpointMapper.selectById(id);
    }
}
