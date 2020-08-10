package com.github.huzhihui.webdeploy.main.config;

import com.github.huzhihui.webdeploy.main.interceptor.permission.PermissionInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by frank-wang on 2018/2/22.
 */
@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //这里全部拦截都可以，只要不放权限注解
        registry.addInterceptor(new PermissionInterceptor()).addPathPatterns("/**").excludePathPatterns("/login");
        super.addInterceptors(registry);
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }


}
