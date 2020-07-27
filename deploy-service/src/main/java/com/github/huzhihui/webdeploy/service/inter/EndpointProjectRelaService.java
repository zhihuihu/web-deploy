/**
 * otoc.com Inc.
 * Copyright (c) 2016-2020 All Rights Reserved.
 */
package com.github.huzhihui.webdeploy.service.inter;

import com.github.huzhihui.webdeploy.entity.EndpointProjectRela;

/**
 * @author huzhihui
 * @version $ v 0.1 2020/7/21 15:24 huzhihui Exp $$
 */
public interface EndpointProjectRelaService {

    int add(EndpointProjectRela endpointProjectRela);

    int modifyById(EndpointProjectRela endpointProjectRela);

    EndpointProjectRela getById(String id);
}
