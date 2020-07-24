package com.github.huzhihui.webdeploy.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class DeployHistory implements Serializable {
    /**  | 表字段 id */
    private String id;

    /** 终端ID | 表字段 endpoint_id */
    private String endpointId;

    /** 终端名称 | 表字段 endpoint_name */
    private String endpointName;

    /** 项目ID | 表字段 project_id */
    private String projectId;

    /** 项目名称 | 表字段 project_name */
    private String projectName;

    /** 状态：10失败，20：成功 | 表字段 status */
    private Integer status;

    /** 描述 | 表字段 describe */
    private String describe;

    /** 历史文件夹 | 表字段 his_file_name */
    private String hisFileName;

    /** 发布文件ID */
    private String deployFileId;

    /** 操作人名称 | 表字段 oper_user_cname */
    private String operUserCname;

    /** 创建时间 | 表字段 create_time */
    private Date createTime;

    /** 执行日志 | 表字段 oper_log */
    private String operLog;

    private static final long serialVersionUID = 1L;

}
