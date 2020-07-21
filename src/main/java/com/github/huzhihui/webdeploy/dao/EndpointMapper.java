package com.github.huzhihui.webdeploy.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.huzhihui.webdeploy.entity.Endpoint;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author huzhihui
 * @version $ v 0.1 2020/7/17 17:45 huzhihui Exp $$
 */
@Repository
public interface EndpointMapper extends BaseMapper<Endpoint> {
    List<Endpoint> selectAll();

    int updateByPrimaryKey(Endpoint record);
}
