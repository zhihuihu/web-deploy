package com.github.huzhihui.webdeploy.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.huzhihui.webdeploy.entity.DeployFile;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeployFileMapper extends BaseMapper<DeployFile> {
    List<DeployFile> selectAll();

    int updateByPrimaryKey(DeployFile record);
}
