package com.iee.orm.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iee.orm.mybatis.entity.File;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 文件 Mapper 接口
 * </p>
 *
 * @author longxiaonan
 * @since 2019-11-20
 */
@Repository
public interface FileMapper extends BaseMapper<File> {

    /**
     * 根据ID获取查询对象
     * @param id
     * @return
     */
    File getFileById(String id);

    List<File> getFilePageList();

}
