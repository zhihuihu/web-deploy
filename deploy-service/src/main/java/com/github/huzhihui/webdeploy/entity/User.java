package com.github.huzhihui.webdeploy.entity;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    /**  | 表字段 id */
    private String id;

    /**  | 表字段 user_name */
    private String userName;

    /** 中文名 | 表字段 user_cname */
    private String userCname;

    /** 盐 | 表字段 salt */
    private String salt;

    /** 密码 | 表字段 password */
    private String password;

    /** 状态  10：启用  20：禁用 | 表字段 use_flag */
    private Integer useFlag;

    /**  | 表字段 create_time */
    private Date createTime;

    /**  | 表字段 update_time */
    private Date updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table user
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

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getUseFlag() {
        return useFlag;
    }

    public void setUseFlag(Integer useFlag) {
        this.useFlag = useFlag;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}