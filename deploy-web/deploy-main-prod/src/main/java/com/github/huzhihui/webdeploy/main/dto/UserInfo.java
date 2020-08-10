/**
 * otoc.com Inc.
 * Copyright (c) 2016-2020 All Rights Reserved.
 */
package com.github.huzhihui.webdeploy.main.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author huzhihui
 * @version $ v 0.1 2020/7/27 14:49 huzhihui Exp $$
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {

    private String userName;

    private String userCname;

    /** 当前终端ID */
    private String endpointId;
    private String endpointName;
}
