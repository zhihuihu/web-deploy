/**
 * otoc.com Inc.
 * Copyright (c) 2016-2020 All Rights Reserved.
 */
package com.github.huzhihui.webdeploy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.github.huzhihui.webdeploy.common.enums.ProjectEnums;
import com.github.huzhihui.webdeploy.common.utils.IdWorkerUtils;
import com.github.huzhihui.webdeploy.dao.ProjectMapper;
import com.github.huzhihui.webdeploy.entity.Project;
import com.github.huzhihui.webdeploy.service.inter.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author huzhihui
 * @version $ v 0.1 2020/7/17 17:42 huzhihui Exp $$
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectMapper projectMapper;

    @Override
    public int add(Project project) {
        project.setId(IdWorkerUtils.getId());
        project.setUseFlag(ProjectEnums.USE_FLAG.ENABLE.getValue());
        project.setCreateTime(new Date());
        return projectMapper.insert(project);
    }

    @Override
    public int modifyById(Project project) {
        project.setUpdateTime(new Date());
        return projectMapper.updateById(project);
    }

    @Override
    public Project getById(String id) {
        return projectMapper.selectById(id);
    }

    @Override
    public List<Project> page(String endpointId,Integer useFlag,String search) {
        LambdaQueryWrapper<Project> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(null != endpointId,Project::getEndpointId,endpointId);
        queryWrapper.eq(null != useFlag,Project::getUseFlag,useFlag);
        queryWrapper.like(StringUtils.isNotEmpty(search),Project::getName,search);
        return projectMapper.selectList(queryWrapper);
    }
}
