/**
 * otoc.com Inc.
 * Copyright (c) 2016-2020 All Rights Reserved.
 */
package com.github.huzhihui.webdeploy.main.handler;

import com.github.huzhihui.webdeploy.main.dto.EndpointSelectResultDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Thymeleaf 模板页面静态支持
 * @author huzhihui
 * @version $ v 0.1 2020/8/7 14:14 huzhihui Exp $$
 */
@Component
public class ThymeleafPageHandler {

    @Autowired
    private EndpointHandler endpointHandler;
    private static ThymeleafPageHandler thymeleafPageHandler;
    @PostConstruct
    protected void init(){
        thymeleafPageHandler = this;
    }

    public static List<EndpointSelectResultDto> buildEndpointSelect(){
        return thymeleafPageHandler.endpointHandler.buildEndpointSelect();
    }
}
