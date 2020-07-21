/**
 * zhihuihu.github.io.
 * Copyright (c) 2016-2019 All Rights Reserved.
 */
package com.github.huzhihui.webdeploy.utils;

import com.github.huzhihui.webdeploy.enums.HttpCodeEnum;
import org.slf4j.Logger;

import java.util.List;
import java.util.Map;

/**
 * 断言工具类
 * @author huzhi
 * @version $ v 0.1 2019/8/18 15:39 huzhi Exp $$
 */
public class AssertUtils {
    /**
     * 断言不为空 如果为null || ""  "  "    抛出异常终止执行
     * @param logger    Logger
     * @param value 待检查值
     * @param msg   异常返回信息
     */
    public static void isNotEmpty(Logger logger, String value, String msg) {
        if (null == value || value.trim().equals("")) {
            throw new BusinessException(HttpCodeEnum.ERROR.getCode(),msg);
        }
    }

    public static void isNotEmpty(Logger logger, String value, Integer code, String msg) {
        if (null == value || value.trim().equals("")) {
            throw new BusinessException(code,msg);
        }
    }

    /**
     * 断言不为空 如果为null 抛出异常终止执行
     * @param logger    Logger
     * @param value 待检查值
     * @param msg   异常返回信息
     */
    public static void isNotNull(Logger logger, Object value, String msg) {
        if (null == value) {
            throw new BusinessException(HttpCodeEnum.ERROR.getCode(),msg);
        }
    }

    public static void isNotNull(Logger logger, Object value, Integer code, String msg) {
        if (null == value) {
            throw new BusinessException(code,msg);
        }
    }

    /**
     * 断言不为空 如果为null || list.size() == 0    抛出异常终止执行
     * @param logger    Logger
     * @param list  待检查值
     * @param msg   异常返回信息
     */
    public static void isNotEmpty(Logger logger, List list, String msg) {
        if (null == list || list.size() == 0) {
            throw new BusinessException(HttpCodeEnum.ERROR.getCode(),msg);
        }
    }

    public static void isNotEmpty(Logger logger, List list, Integer code, String msg) {
        if (null == list || list.size() == 0) {
            throw new BusinessException(code,msg);
        }
    }

    /**
     * 断言不为空 如果为null || map.size() == 0     抛出异常终止执行
     * @param logger    Logger
     * @param map   待检查值
     * @param msg   异常返回信息
     */
    public static void isNotEmpty(Logger logger, Map map, String msg) {
        if (null == map || map.size() == 0) {
            throw new BusinessException(HttpCodeEnum.ERROR.getCode(),msg);
        }
    }

    public static void isNotEmpty(Logger logger, Map map, Integer code, String msg) {
        if (null == map || map.size() == 0) {
            throw new BusinessException(code,msg);
        }
    }

    /**
     * 断言为空 如果为不为null 抛出异常终止执行
     * @param logger    Logger
     * @param value 待检查值
     * @param msg    异常返回信息
     */
    public static void isNull(Logger logger, Object value, String msg) {
        if (null != value) {
            throw new BusinessException(HttpCodeEnum.ERROR.getCode(),msg);
        }
    }

    public static void isNull(Logger logger, Object value, Integer code, String msg) {
        if (null != value) {
            throw new BusinessException(code,msg);
        }
    }

    /**
     * 断言为true 如果为不为true 抛出异常终止执行
     * @param logger
     * @param value
     * @param msg
     */
    public static void isTrue(Logger logger, Boolean value, String msg){
        if(null == value || false == value){
            throw new BusinessException(HttpCodeEnum.ERROR.getCode(),msg);
        }
    }

    public static void isTrue(Logger logger, Boolean value, Integer code, String msg){
        if(null == value || false == value){
            throw new BusinessException(code,msg);
        }
    }

    /**
     * 断言为true 如果为不为false 抛出异常终止执行
     * @param logger
     * @param value
     * @param msg
     */
    public static void isFalse(Logger logger, Boolean value, String msg){
        if(null == value || true == value){
            throw new BusinessException(HttpCodeEnum.ERROR.getCode(),msg);
        }
    }

    public static void isFalse(Logger logger, Boolean value, Integer code, String msg){
        if(null == value || true == value){
            throw new BusinessException(code,msg);
        }
    }

}
