/**
 * otoc.com Inc.
 * Copyright (c) 2016-2019 All Rights Reserved.
 */
package com.github.huzhihui.webdeploy.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author huzhihui
 * @version $ v 0.1 2019/8/20 11:09 huzhihui Exp $$
 */
public class HttpServletResponseJsonUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpServletResponseJsonUtils.class);

    /**
     * 返回json对象到前端
     * @param response
     * @param object
     */
    public static void sendJsonObject(HttpServletResponse response, Object object){
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.append(JackSonUtils.objectToJsonStr(object));
        } catch (Exception ex) {
            LOGGER.error("HttpServletResponse 返回JSON失败", ex);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
