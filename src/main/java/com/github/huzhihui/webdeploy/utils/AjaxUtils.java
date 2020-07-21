/**
 * otoc.com Inc.
 * Copyright (c) 2016-2019 All Rights Reserved.
 */
package com.github.huzhihui.webdeploy.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author huzhihui
 * @version $ v 0.1 2019/8/20 16:41 huzhihui Exp $$
 */
public class AjaxUtils {

    /**
     * 判断是否是ajax请求
     * @param request
     * @return
     */
    public static boolean isAjax(HttpServletRequest request){
        if (request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")){
            return true;
        }
        return false;
    }
}
