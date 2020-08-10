/**
 * otoc.com Inc.
 * Copyright (c) 2016-2020 All Rights Reserved.
 */
package com.github.huzhihui.webdeploy.main.handler;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.github.huzhihui.webdeploy.common.constant.ConstantKey;
import com.github.huzhihui.webdeploy.common.enums.EndpointEnums;
import com.github.huzhihui.webdeploy.entity.Endpoint;
import com.github.huzhihui.webdeploy.main.dto.EndpointSelectResultDto;
import com.github.huzhihui.webdeploy.main.dto.UserInfo;
import com.github.huzhihui.webdeploy.main.utils.SessionUtils;
import com.github.huzhihui.webdeploy.service.inter.EndpointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 终端助手
 * @author huzhihui
 * @version $ v 0.1 2020/8/7 13:57 huzhihui Exp $$
 */
@Component
public class EndpointHandler {

    @Autowired
    private EndpointService endpointService;

    public List<EndpointSelectResultDto> buildEndpointSelect(){
        List<EndpointSelectResultDto> endpointSelectResultDtos = new ArrayList<>();
        List<Endpoint> endpoints = endpointService.page(EndpointEnums.USE_FLAG.ENABLE.getValue(),null);
        UserInfo userInfo = SessionUtils.getUserInfo();
        if(StringUtils.isEmpty(userInfo.getEndpointId())){
            Endpoint endpointL = endpoints.stream().filter(endpoint -> endpoint.getTerminalNum().equals(ConstantKey.master_terminal_num)).findFirst().get();
            userInfo.setEndpointId(endpointL.getId());
            userInfo.setEndpointName(endpointL.getName());
        }
        endpoints.stream().forEach(endpoint -> {
            EndpointSelectResultDto endpointSelectResultDto = new EndpointSelectResultDto();
            endpointSelectResultDto.setId(endpoint.getId());
            endpointSelectResultDto.setName(endpoint.getName());
            endpointSelectResultDto.setSelected(endpoint.getId().equals(userInfo.getEndpointId()));
            endpointSelectResultDtos.add(endpointSelectResultDto);
        });
        return endpointSelectResultDtos;
    }
}
