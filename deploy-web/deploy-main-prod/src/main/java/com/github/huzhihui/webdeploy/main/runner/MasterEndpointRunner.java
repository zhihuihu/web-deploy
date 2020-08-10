/**
 * otoc.com Inc.
 * Copyright (c) 2016-2020 All Rights Reserved.
 */
package com.github.huzhihui.webdeploy.main.runner;

import com.github.huzhihui.webdeploy.common.constant.ConstantKey;
import com.github.huzhihui.webdeploy.entity.Endpoint;
import com.github.huzhihui.webdeploy.service.inter.EndpointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 注册master节点
 * @author huzhihui
 * @version $ v 0.1 2020/8/7 11:15 huzhihui Exp $$
 */
@Component
public class MasterEndpointRunner implements CommandLineRunner {

    @Autowired
    private EndpointService endpointService;

    @Override
    public void run(String... args) throws Exception {
        Endpoint endpoint = endpointService.getByTerminalNum(ConstantKey.master_terminal_num);
        if(null == endpoint){
            endpoint = new Endpoint();
            endpoint.setName("主节点");
            endpoint.setHost("127.0.0.1");
            endpoint.setTerminalNum(ConstantKey.master_terminal_num);
            endpointService.add(endpoint);
        }
    }
}
