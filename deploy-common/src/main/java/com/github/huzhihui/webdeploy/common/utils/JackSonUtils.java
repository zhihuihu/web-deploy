/**
 * otoc.com Inc.
 * Copyright (c) 2016-2019 All Rights Reserved.
 */
package com.github.huzhihui.webdeploy.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author huzhihui
 * @version $ v 0.1 2019/8/20 11:16 huzhihui Exp $$
 */
public class JackSonUtils {

    private static Logger LOGGER = LoggerFactory.getLogger(JackSonUtils.class);

    /**
     * 将json字符串转化为对应的Object对象
     * @param jsonStr   字符串
     * @param clazz 对象的class
     * @param <T>   泛型对象
     * @return  输入的对象
     */
    public static <T> T jsonStrToObject(String jsonStr, Class<T> clazz) {
        if (null == jsonStr) {
            return null;
        }
        try {
            return new ObjectMapper().readValue(jsonStr, clazz);
        } catch (IOException e) {
            throw new RuntimeException("JackSon转换出错",e);
        }
    }

    /**
     * 将对象转化为json字符串
     * @param object    输入对象
     * @return  对象字符串
     */
    public static String objectToJsonStr(Object object) {
        if (null == object) {
            return null;
        }
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JackSon转换出错",e);
        }
    }
}
