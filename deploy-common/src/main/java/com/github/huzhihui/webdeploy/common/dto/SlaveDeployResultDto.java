/**
 * otoc.com Inc.
 * Copyright (c) 2016-2020 All Rights Reserved.
 */
package com.github.huzhihui.webdeploy.common.dto;

import lombok.Data;

/**
 * 从节点发布结果DTO
 * @author huzhihui
 * @version $ v 0.1 2020/8/11 13:20 huzhihui Exp $$
 */
@Data
public class SlaveDeployResultDto {

    private Boolean result;

    private String operLog;
}
