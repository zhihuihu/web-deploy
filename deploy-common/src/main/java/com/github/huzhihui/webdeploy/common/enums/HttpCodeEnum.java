/**
 * zhihuihu.github.io.
 * Copyright (c) 2016-2019 All Rights Reserved.
 */
package com.github.huzhihui.webdeploy.common.enums;

/**
 * http请求码枚举
 * @author huzhi
 * @version $ v 0.1 2019/8/18 14:53 huzhi Exp $$
 */
public enum HttpCodeEnum {

    OK(0,"请求成功"),
    ERROR(-10,"请求失败"),
    ERROR_PARAM(-11,"传入数据错误"),
    RESUBMIT_ERROR(-12,"提交速度太快"),


    NOT_LOGIN(-20,"登陆过期"),

    ;

    int code;

    String codeMessage;

    HttpCodeEnum(int code, String codeMessage) {
        this.code = code;
        this.codeMessage = codeMessage;
    }

    public int getCode() {
        return code;
    }

    public String getCodeMessage() {
        return codeMessage;
    }
}
