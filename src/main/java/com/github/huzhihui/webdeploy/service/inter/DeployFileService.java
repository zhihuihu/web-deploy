/**
 * otoc.com Inc.
 * Copyright (c) 2016-2020 All Rights Reserved.
 */
package com.github.huzhihui.webdeploy.service.inter;

import com.github.huzhihui.webdeploy.entity.DeployFile;

/**
 * @author huzhihui
 * @version $ v 0.1 2020/7/24 9:59 huzhihui Exp $$
 */
public interface DeployFileService {

    int add(DeployFile deployFile);

    DeployFile getById(String id);
}
