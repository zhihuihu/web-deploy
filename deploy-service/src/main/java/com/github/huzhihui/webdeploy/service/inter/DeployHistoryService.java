/**
 * otoc.com Inc.
 * Copyright (c) 2016-2020 All Rights Reserved.
 */
package com.github.huzhihui.webdeploy.service.inter;

import com.github.huzhihui.webdeploy.entity.DeployHistory;

import java.util.List;

/**
 * @author huzhihui
 * @version $ v 0.1 2020/7/17 17:35 huzhihui Exp $$
 */
public interface DeployHistoryService {

    int add(DeployHistory deployHistory);

    int modifyById(DeployHistory deployHistory);

    DeployHistory getById(String id);

    /**
     * 分页
     * @param endpointId
     * @param projectId
     * @return
     */
    List<DeployHistory> page(String endpointId,String projectId);

}
