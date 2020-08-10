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
public class DeployHistoryEnums {

    /**
     * 状态：10失败，20：成功
     */
    public enum STATUS {
        FAIL(10, "失败"),
        SUCCESS(20, "成功"),
        ;

        private Integer value;

        private String desc;

        STATUS(Integer value, String desc) {
            this.value = value;
            this.desc = desc;
        }

        public static STATUS getByValue(Integer value) {
            if (value == null) {
                return null;
            }
            return Arrays.stream(STATUS.values()).filter(type -> value.equals(type.getValue())).findFirst().orElse(null);
        }

        public static STATUS getByDesc(String desc) {
            return Arrays.stream(STATUS.values()).filter(type -> desc.equals(type.getDesc())).findFirst().orElse(null);
        }

        public Integer getValue() {
            return value;
        }

        public String getDesc() {
            return desc;
        }
    }
}
