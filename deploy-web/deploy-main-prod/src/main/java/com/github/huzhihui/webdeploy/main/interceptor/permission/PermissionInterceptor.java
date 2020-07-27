/**
 * otoc.com Inc.
 * Copyright (c) 2016-2019 All Rights Reserved.
 */
package com.github.huzhihui.webdeploy.main.interceptor.permission;

import com.github.huzhihui.webdeploy.common.enums.HttpCodeEnum;
import com.github.huzhihui.webdeploy.common.utils.BusinessException;
import com.github.huzhihui.webdeploy.main.dto.UserInfo;
import com.github.huzhihui.webdeploy.main.utils.SessionUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 权限拦截器
 * @author huzhihui
 * @version $ v 0.1 2019/7/17 14:07 huzhihui Exp $$
 */
public class PermissionInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler;
            Permission permission = hm.getMethod().getAnnotation(Permission.class);
            if (permission != null) {
                UserInfo userInfo = SessionUtils.getUserInfo();
                if(userInfo == null){
                    throw new BusinessException(HttpCodeEnum.NOT_LOGIN.getCode(),HttpCodeEnum.NOT_LOGIN.getCodeMessage());
                }
            }
        }
        return true;
    }
}
