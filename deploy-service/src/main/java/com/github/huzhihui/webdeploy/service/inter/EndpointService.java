/**
 * otoc.com Inc.
 * Copyright (c) 2016-2020 All Rights Reserved.
 */
package com.github.huzhihui.webdeploy.service.inter;

import com.github.huzhihui.webdeploy.entity.Endpoint;

import java.util.List;

/**
 * @author huzhihui
 * @version $ v 0.1 2020/7/21 15:24 huzhihui Exp $$
 */
public interface EndpointService {

    int add(Endpoint endpoint);

    int modifyById(Endpoint endpoint);

    Endpoint getById(String id);

    Endpoint getByTerminalNum(String terminalNum);

    List<Endpoint> page(Integer useFlag,String search);
}
