/**
 * otoc.com Inc.
 * Copyright (c) 2016-2020 All Rights Reserved.
 */
package com.github.huzhihui.webdeploy.common.enums;

import java.util.Arrays;

/**
 * @author huzhihui
 * @version $ v 0.1 2020/8/6 16:53 huzhihui Exp $$
 */
public class UserEnums {

    /**
     * 状态  10：启用  20：禁用
     */
    public enum USE_FLAG {
        ENABLE(10, "启用"),
        DISABLE(20, "禁用"),
        ;

        private Integer value;

        private String desc;

        USE_FLAG(Integer value, String desc) {
            this.value = value;
            this.desc = desc;
        }

        public static USE_FLAG getByValue(Integer value) {
            if (value == null) {
                return null;
            }
            return Arrays.stream(USE_FLAG.values()).filter(type -> value.equals(type.getValue())).findFirst().orElse(null);
        }

        public static USE_FLAG getByDesc(String desc) {
            return Arrays.stream(USE_FLAG.values()).filter(type -> desc.equals(type.getDesc())).findFirst().orElse(null);
        }

        public Integer getValue() {
            return value;
        }

        public String getDesc() {
            return desc;
        }
    }
}
