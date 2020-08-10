/**
 * otoc.com Inc.
 * Copyright (c) 2016-2020 All Rights Reserved.
 */
package com.github.huzhihui.webdeploy.common.utils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * 签名工具类
 * @author huzhihui
 * @version $ v 0.1 2020/8/7 17:19 huzhihui Exp $$
 */
public class SignUtils {

    public static final String HMACSHA256 = "HmacSHA256";

    public static final String MD5 = "MD5";

    public static String sign(Map<String,String> param, String mchKey, String signType) throws Exception{
        List<String> keys = new ArrayList<>(param.keySet());
        Collections.sort(keys);
        StringBuffer authInfo = new StringBuffer();
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            if(i == 0){
                authInfo.append(key).append("=").append(param.get(key));
            }else{
                authInfo.append("&").append(key).append("=").append(param.get(key));
            }
        }
        authInfo.append("&key=").append(mchKey);
        if(signType.equals(MD5)){
            return MD5(authInfo.toString()).toUpperCase();
        }else if(signType.equals(HMACSHA256)){
            return hmacSha256Sign(authInfo.toString(),mchKey);
        }
        return null;
    }

    private static String MD5(String str) throws Exception{
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(str.getBytes());
            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            throw new Exception("MD5加密出现错误");
        }
    }

    private static String hmacSha256Sign(String message, String rsaPriKey) throws Exception{
        try {
            Mac sha256 = Mac.getInstance(HMACSHA256);
            SecretKeySpec secretKeySpec = new SecretKeySpec(rsaPriKey.getBytes(StandardCharsets.UTF_8), HMACSHA256);
            sha256.init(secretKeySpec);
            byte[] bytes = sha256.doFinal(message.getBytes(StandardCharsets.UTF_8));
            return byteToHex(bytes).toUpperCase();
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
            throw e;
        }
    }

    private static String byteToHex(byte[] bytes){
        String strHex = "";
        StringBuilder sb = new StringBuilder("");
        for (int n = 0; n < bytes.length; n++) {
            strHex = Integer.toHexString(bytes[n] & 0xFF);
            sb.append((strHex.length() == 1) ? "0" + strHex : strHex);
        }
        return sb.toString().trim();
    }

}
