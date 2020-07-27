package com.github.huzhihui.webdeploy.entity;

import java.io.Serializable;
import java.util.Date;

public class DeployFile implements Serializable {
    /**  | 表字段 id */
    private String id;

    /**  | 表字段 name */
    private String name;

    /**  | 表字段 create_time */
    private Date createTime;

    /** 文件 | 表字段 source */
    private byte[] source;

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public byte[] getSource() {
        return source;
    }

    public void setSource(byte[] source) {
        this.source = source;
    }
}
