package com.github.huzhihui.webdeploy.main;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * web前端发布系统
 * @author huzhihui
 */
@MapperScan("com.github.huzhihui.webdeploy.dao")
@SpringBootApplication(scanBasePackages = {"com.github.huzhihui.webdeploy"})
public class WebDeployApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebDeployApplication.class, args);
    }

}
