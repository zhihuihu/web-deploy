/**
 * otoc.com Inc.
 * Copyright (c) 2016-2020 All Rights Reserved.
 */
package com.github.huzhihui.webdeploy.service.inter;

import com.github.huzhihui.webdeploy.entity.Project;

import java.util.List;

/**
 * @author huzhihui
 * @version $ v 0.1 2020/7/17 17:36 huzhihui Exp $$
 */
public interface ProjectService {

    int add(Project project);

    int modifyById(Project project);

    Project getById(String id);

    List<Project> page(String endpointId,Integer useFlag,String search);

}
