/**
 * zhihuihu.github.io.
 * Copyright (c) 2016-2019 All Rights Reserved.
 */
package com.github.huzhihui.webdeploy.main.config.exception;

import com.github.huzhihui.webdeploy.common.enums.HttpCodeEnum;
import com.github.huzhihui.webdeploy.common.utils.AjaxUtils;
import com.github.huzhihui.webdeploy.common.utils.BusinessException;
import com.github.huzhihui.webdeploy.common.utils.HttpServletResponseJsonUtils;
import com.github.huzhihui.webdeploy.common.utils.ResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author huzhi
 * @version $ v 0.1 2019/8/3 20:53 huzhi Exp $$
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(HttpServletRequest request, HttpServletResponse response, Exception e) {
        LOGGER.error("服务运行异常", e);
        if(AjaxUtils.isAjax(request)){
            HttpServletResponseJsonUtils.sendJsonObject(response, ResponseMessage.failure("服务运行异常"));
            return null;
        }
        ModelAndView modelAndView = new ModelAndView("redirect:/error/500");
        return modelAndView;
    }

    /**
     * 业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public ModelAndView handleBusinessException(HttpServletRequest request, HttpServletResponse response, BusinessException e) {
        if(AjaxUtils.isAjax(request)){
            HttpServletResponseJsonUtils.sendJsonObject(response,ResponseMessage.failure(e.getCode(), e.getMessage()));
            return null;
        }
        ModelAndView modelAndView = new ModelAndView("redirect:/error/500");
        if(e.getCode().equals(HttpCodeEnum.NOT_LOGIN.getCode())){
            modelAndView = new ModelAndView("redirect:/login");
        }
        return modelAndView;
    }

}
