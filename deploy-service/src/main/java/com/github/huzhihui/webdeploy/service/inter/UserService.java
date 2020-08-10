/**
 * otoc.com Inc.
 * Copyright (c) 2016-2020 All Rights Reserved.
 */
package com.github.huzhihui.webdeploy.service.inter;

import com.github.huzhihui.webdeploy.entity.User;

import java.util.List;

/**
 * @author huzhihui
 * @version $ v 0.1 2020/7/17 17:32 huzhihui Exp $$
 */
public interface UserService {

    int add(User user);

    int modifyById(User user);

    User getById(String id);

    List<User> page(String userName,String userCname);

    User getByUserName(String userName);

}
