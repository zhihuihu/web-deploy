/**
 * otoc.com Inc.
 * Copyright (c) 2016-2019 All Rights Reserved.
 */
package com.github.huzhihui.webdeploy.utils;

import com.github.huzhihui.webdeploy.dto.UserInfo;

/**
 * Session工具类
 * @author huzhihui
 * @version $ v 0.1 2019/7/17 13:59 huzhihui Exp $$
 */
public class SessionUtils {

    /**
     * 设置用户信息
     * @param userInfo
     */
    public static void setUserInfo(UserInfo userInfo){
        HttpServletRequestUtils.getHttpSession().setAttribute("userInfo",userInfo);
    }

    /**
     * 获取用户信息
     * @return
     */
    public static UserInfo getUserInfo(){
        return (UserInfo) HttpServletRequestUtils.getHttpSession().getAttribute("userInfo");
    }
}
