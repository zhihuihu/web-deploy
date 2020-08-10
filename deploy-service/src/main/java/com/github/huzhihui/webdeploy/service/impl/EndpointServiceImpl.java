/**
 * otoc.com Inc.
 * Copyright (c) 2016-2020 All Rights Reserved.
 */
package com.github.huzhihui.webdeploy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.github.huzhihui.webdeploy.common.utils.IdWorkerUtils;
import com.github.huzhihui.webdeploy.dao.EndpointMapper;
import com.github.huzhihui.webdeploy.entity.Endpoint;
import com.github.huzhihui.webdeploy.service.inter.EndpointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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

    @Override
    public Endpoint getByTerminalNum(String terminalNum) {
        LambdaQueryWrapper<Endpoint> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Endpoint::getTerminalNum,terminalNum);
        return endpointMapper.selectOne(queryWrapper);
    }

    @Override
    public List<Endpoint> page(Integer useFlag,String search) {
        LambdaQueryWrapper<Endpoint> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(null != useFlag,Endpoint::getUseFlag,useFlag);
        queryWrapper.like(StringUtils.isNotEmpty(search),Endpoint::getName,search);
        return endpointMapper.selectList(queryWrapper);
    }
}
