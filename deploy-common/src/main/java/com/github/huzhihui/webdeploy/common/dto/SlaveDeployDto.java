/**
 * otoc.com Inc.
 * Copyright (c) 2016-2020 All Rights Reserved.
 */
package com.github.huzhihui.webdeploy.common.dto;

import lombok.Data;

/**
 * 从节点发布DTO
 * @author huzhihui
 * @version $ v 0.1 2020/8/11 11:02 huzhihui Exp $$
 */
@Data
public class SlaveDeployDto {

    /** 项目名称 */
    private String projectName;
    /** 根目录 */
    private String rootFolder;
    /** 发布文件夹名称 */
    private String deployFolder;
    /** 打包后的文件夹校验名称 */
    private String packageFolder;
    /** 发布文件名称 */
    private String fileName;

}
