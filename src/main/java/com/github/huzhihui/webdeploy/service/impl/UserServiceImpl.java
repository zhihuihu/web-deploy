/**
 * otoc.com Inc.
 * Copyright (c) 2016-2020 All Rights Reserved.
 */
package com.github.huzhihui.webdeploy.service.impl;

import com.github.huzhihui.webdeploy.dao.UserMapper;
import com.github.huzhihui.webdeploy.entity.User;
import com.github.huzhihui.webdeploy.service.inter.UserService;
import com.github.huzhihui.webdeploy.utils.IdWorkerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author huzhihui
 * @version $ v 0.1 2020/7/17 17:41 huzhihui Exp $$
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public int add(User user) {
        user.setId(IdWorkerUtils.getId());
        user.setCreateTime(new Date());
        return userMapper.insert(user);
    }

    @Override
    public int modifyById(User user) {
        user.setUpdateTime(new Date());
        return userMapper.updateById(user);
    }

    @Override
    public User getById(String id) {
        return userMapper.selectById(id);
    }
}
