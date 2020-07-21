/**
 * otoc.com Inc.
 * Copyright (c) 2016-2020 All Rights Reserved.
 */
package com.github.huzhihui.webdeploy.service.impl;

import com.github.huzhihui.webdeploy.dao.EndpointProjectRelaMapper;
import com.github.huzhihui.webdeploy.entity.EndpointProjectRela;
import com.github.huzhihui.webdeploy.service.inter.EndpointProjectRelaService;
import com.github.huzhihui.webdeploy.utils.IdWorkerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author huzhihui
 * @version $ v 0.1 2020/7/21 15:25 huzhihui Exp $$
 */
@Service
public class EndpointProjectRelaServiceImpl implements EndpointProjectRelaService {

    @Autowired
    private EndpointProjectRelaMapper endpointProjectRelaMapper;

    @Override
    public int add(EndpointProjectRela endpointProjectRela) {
        endpointProjectRela.setId(IdWorkerUtils.getId());
        endpointProjectRela.setCreateTime(new Date());
        return endpointProjectRelaMapper.insert(endpointProjectRela);
    }

    @Override
    public int modifyById(EndpointProjectRela endpointProjectRela) {
        return endpointProjectRelaMapper.updateByPrimaryKey(endpointProjectRela);
    }

    @Override
    public EndpointProjectRela getById(String id) {
        return endpointProjectRelaMapper.selectById(id);
    }
}
