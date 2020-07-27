package com.github.huzhihui.webdeploy.entity;

import java.io.Serializable;
import java.util.Date;

public class Endpoint implements Serializable {
    /**  | 表字段 id */
    private String id;

    /** 名称 | 表字段 name */
    private String name;

    /** 访问host | 表字段 host */
    private String host;

    /** 端口 | 表字段 port */
    private Integer port;

    /** 终端号 | 表字段 terminal_num */
    private String terminalNum;

    /** 参数签名秘钥 | 表字段 sign_key */
    private String signKey;

    /**  | 表字段 create_time */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table endpoint
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getTerminalNum() {
        return terminalNum;
    }

    public void setTerminalNum(String terminalNum) {
        this.terminalNum = terminalNum;
    }

    public String getSignKey() {
        return signKey;
    }

    public void setSignKey(String signKey) {
        this.signKey = signKey;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}