/**
 * otoc.com Inc.
 * Copyright (c) 2016-2020 All Rights Reserved.
 */
package com.github.huzhihui.webdeploy.slave.controller;

import com.github.huzhihui.webdeploy.common.utils.ResponseMessage;
import com.github.huzhihui.webdeploy.common.utils.SignUtils;
import com.github.huzhihui.webdeploy.slave.config.WebPublishProperties;
import com.github.huzhihui.webdeploy.slave.utils.SignCheckUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author huzhihui
 * @version $ v 0.1 2020/8/10 17:33 huzhihui Exp $$
 */
@Slf4j
@Controller
@RequestMapping(value = "heart")
public class HeartController {

    @Autowired
    private WebPublishProperties webPublishProperties;

    /**
     * 状态检查
     * @param timeStamp
     * @param terminalNum
     * @param sign
     * @return
     */
    @RequestMapping(value = "check/state")
    @ResponseBody
    public ResponseMessage checkState(@RequestParam("timeStamp") String timeStamp,
                                      @RequestParam("terminalNum") String terminalNum,
                                      @RequestParam("sign") String sign){
        boolean check = SignCheckUtils.checkSign(timeStamp,terminalNum,sign,webPublishProperties.getTerminalNum(),webPublishProperties.getSignKey());
        if(!check){
            return ResponseMessage.failure("签名验证失败");
        }
        return ResponseMessage.success();
    }
}
