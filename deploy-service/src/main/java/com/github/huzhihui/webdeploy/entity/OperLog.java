package com.github.huzhihui.webdeploy.entity;

import java.io.Serializable;
import java.util.Date;

public class OperLog implements Serializable {
    /**  | 表字段 id */
    private String id;

    /** 描述 | 表字段 desc */
    private String desc;

    /** 用户ID | 表字段 user_id */
    private String userId;

    /** 用户名 | 表字段 user_name */
    private String userName;

    /** 用户中文名 | 表字段 user_cname */
    private String userCname;

    /**  | 表字段 create_time */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table oper_log
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserCname() {
        return userCname;
    }

    public void setUserCname(String userCname) {
        this.userCname = userCname;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
