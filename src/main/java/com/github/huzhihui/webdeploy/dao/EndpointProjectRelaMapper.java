package com.github.huzhihui.webdeploy.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.huzhihui.webdeploy.entity.EndpointProjectRela;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author huzhihui
 * @version $ v 0.1 2020/7/17 17:45 huzhihui Exp $$
 */
@Repository
public interface EndpointProjectRelaMapper extends BaseMapper<EndpointProjectRela> {
    List<EndpointProjectRela> selectAll();

    int updateByPrimaryKey(EndpointProjectRela record);
}
