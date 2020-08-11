/**
 * otoc.com Inc.
 * Copyright (c) 2016-2020 All Rights Reserved.
 */
package com.github.huzhihui.webdeploy.slave.utils;

import com.github.huzhihui.webdeploy.common.utils.SignUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @author huzhihui
 * @version $ v 0.1 2020/8/10 17:44 huzhihui Exp $$
 */
@Slf4j
public class SignCheckUtils {

    public static Boolean checkSign(String timeStamp,String terminalNum,String sign,String localTerminalNum, String signKey){
        Boolean result = false;
        if(!terminalNum.equals(localTerminalNum)){
            return result;
        }
        Map map = new HashMap<String,String>(){{
            put("timeStamp",timeStamp);
            put("terminalNum",terminalNum);
        }};
        try{
            String localSign = SignUtils.sign(map,signKey,SignUtils.HMACSHA256);
            if(localSign.equals(sign)){
                result = true;
            }
        }catch (Exception ex){
            log.error("签名验证失败",ex);
        }
        return result;
    }
}
