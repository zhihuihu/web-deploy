package com.github.huzhihui.webdeploy.main.interceptor.permission;

import java.lang.annotation.*;

/**
 * 权限拦截器
 * @author huzhihui
 * @version $ v 0.1 2019/7/17 14:06 huzhihui Exp $$
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Permission {
    String value() default "";
}
