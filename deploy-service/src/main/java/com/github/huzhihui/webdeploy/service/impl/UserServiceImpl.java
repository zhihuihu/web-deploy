/**
 * otoc.com Inc.
 * Copyright (c) 2016-2020 All Rights Reserved.
 */
package com.github.huzhihui.webdeploy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.huzhihui.webdeploy.common.enums.HttpCodeEnum;
import com.github.huzhihui.webdeploy.common.utils.BusinessException;
import com.github.huzhihui.webdeploy.common.utils.IdWorkerUtils;
import com.github.huzhihui.webdeploy.common.utils.MD5Utils;
import com.github.huzhihui.webdeploy.dao.UserMapper;
import com.github.huzhihui.webdeploy.entity.User;
import com.github.huzhihui.webdeploy.service.inter.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author huzhihui
 * @version $ v 0.1 2020/7/17 17:41 huzhihui Exp $$
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public int add(User user) {
        user.setId(IdWorkerUtils.getId());
        user.setCreateTime(new Date());
        user.setSalt(UUID.randomUUID().toString().replace("-",""));
        try{
            user.setPassword(MD5Utils.MD5(user.getPassword()+user.getSalt()).toUpperCase());
        }catch (Exception ex){
            throw new BusinessException(HttpCodeEnum.ERROR.getCode(),"MD5加密出错");
        }
        return userMapper.insert(user);
    }

    @Override
    public int modifyById(User user) {
        user.setUpdateTime(new Date());
        if(!StringUtils.isEmpty(user.getPassword())){
            User oldUser = userMapper.selectById(user.getId());
            try{
                user.setPassword(MD5Utils.MD5(user.getPassword()+oldUser.getSalt()).toUpperCase());
            }catch (Exception ex){
                throw new BusinessException(HttpCodeEnum.ERROR.getCode(),"MD5加密出错");
            }

        }
        return userMapper.updateById(user);
    }

    @Override
    public User getById(String id) {
        return userMapper.selectById(id);
    }

    @Override
    public List<User> page(String userName, String userCname) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(!StringUtils.isEmpty(userName),User::getUserName,userName);
        queryWrapper.like(!StringUtils.isEmpty(userCname),User::getUserCname,userCname);
        queryWrapper.orderByDesc(User::getCreateTime);
        return userMapper.selectList(queryWrapper);
    }

    @Override
    public User getByUserName(String userName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,userName);
        return userMapper.selectOne(queryWrapper);
    }
}
