/**
 * otoc.com Inc.
 * Copyright (c) 2016-2020 All Rights Reserved.
 */
package com.github.huzhihui.webdeploy.main.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author huzhihui
 * @version $ v 0.1 2020/5/18 16:48 huzhihui Exp $$
 */
@Data
@ConfigurationProperties(prefix = "web-publish")
public class WebPublishProperties {

    /** 临时空间 */
    private String tempFolder;
    /** shell脚本空间 */
    private String shellFolder;
    /** shell脚本名称 */
    private String shellFileName;
}
