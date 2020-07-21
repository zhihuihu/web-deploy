/**
 * otoc.com Inc.
 * Copyright (c) 2016-2019 All Rights Reserved.
 */
package com.github.huzhihui.webdeploy.dto;

import java.io.Serializable;

/**
 * 用户信息
 * @author huzhihui
 * @version $ v 0.1 2019/7/17 13:57 huzhihui Exp $$
 */
public class UserInfo implements Serializable {

    /** 用户名 */
    private String userName;
    /** 中文名 */
    private String userCname;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserCname() {
        return userCname;
    }

    public void setUserCname(String userCname) {
        this.userCname = userCname;
    }
}
