package com.github.huzhihui.webdeploy.slave;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * web前端发布系统
 * @author huzhihui
 */
@SpringBootApplication(scanBasePackages = {"com.github.huzhihui.webdeploy"},
        exclude= {DataSourceAutoConfiguration.class})
public class WebDeploySlaveApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebDeploySlaveApplication.class, args);
    }

}
