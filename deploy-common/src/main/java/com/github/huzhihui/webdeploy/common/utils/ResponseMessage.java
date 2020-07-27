/**
 * zhihuihu.github.io.
 * Copyright (c) 2016-2019 All Rights Reserved.
 */
package com.github.huzhihui.webdeploy.common.utils;

import com.github.huzhihui.webdeploy.common.enums.HttpCodeEnum;
import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author huzhi
 * @version $ v 0.1 2019/8/3 14:30 huzhi Exp $$
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseMessage<T> implements Serializable {

    private boolean success = true;

    private int code;

    private String codeMessage = "success";

    private T data;

    /**
     * 成功
     * @return
     */
    public static ResponseMessage success(){
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setSuccess(true);
        return responseMessage;
    }

    /**
     * 成功
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResponseMessage<T> success(T data){
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setSuccess(true);
        responseMessage.setData(data);
        return responseMessage;
    }

    /**
     * 成功
     * @param codeMessage
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResponseMessage<T> success(String codeMessage,T data){
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setSuccess(true);
        responseMessage.setCodeMessage(codeMessage);
        responseMessage.setData(data);
        return responseMessage;
    }

    /**
     * 成功分页数据
     * @param pageInfo
     * @return
     */
    public static ResponseMessage success(PageInfo pageInfo) {
        ResponsePageMessage responseMessage = new ResponsePageMessage();
        responseMessage.setCount(pageInfo.getTotal());
        responseMessage.setPages(pageInfo.getPages());
        responseMessage.setCurrentPage(pageInfo.getPageNum());
        responseMessage.setPageSize(pageInfo.getPageSize());
        responseMessage.setData(pageInfo.getList());
        return responseMessage;
    }

    /**
     * 失败
     * @return
     */
    public static ResponseMessage failure(){
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setCode(HttpCodeEnum.ERROR.getCode());
        responseMessage.setSuccess(false);
        return responseMessage;
    }

    /**
     * 失败
     * @param codeMessage
     * @return
     */
    public static ResponseMessage failure(String codeMessage){
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setCode(HttpCodeEnum.ERROR.getCode());
        responseMessage.setSuccess(false);
        responseMessage.setCodeMessage(codeMessage);
        return responseMessage;
    }

    /**
     * 失败
     * @param codeMessage
     * @param code
     * @param <T>
     * @return
     */
    public static <T> ResponseMessage<T> failure(int code,String codeMessage){
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setSuccess(false);
        responseMessage.setCode(code);
        responseMessage.setCodeMessage(codeMessage);
        return responseMessage;
    }

    /**
     * 失败
     * @param codeMessage
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResponseMessage<T> failure(String codeMessage,T data){
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setCode(HttpCodeEnum.ERROR.getCode());
        responseMessage.setSuccess(false);
        responseMessage.setCodeMessage(codeMessage);
        responseMessage.setData(data);
        return responseMessage;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResponsePageMessage<T> extends ResponseMessage<T>{
        /** 总记录数 */
        private Long count;
        /** 总页数 */
        private Integer pages;
        /** 当前页码 */
        private Integer currentPage;
        /** 分页大小 */
        private Integer pageSize;

    }
}
