/**
 * otoc.com Inc.
 * Copyright (c) 2016-2020 All Rights Reserved.
 */
package com.github.huzhihui.webdeploy.main.dto;

import lombok.Data;

import java.util.Date;

/**
 * 终端状态DTO
 * @author huzhihui
 * @version $ v 0.1 2020/8/7 16:41 huzhihui Exp $$
 */
@Data
public class EndpointStatusDto {

    private String id;

    private String name;

    private String host;

    private Integer port;

    private Boolean run;

    private Date createTime;

}
