package com.iee.orm.mybatis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iee.orm.mybatis.entity.File;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 文件 服务类
 * </p>
 *
 * @author longxiaonan
 * @since 2019-11-20
 */
public interface FileService extends IService<File> {

    File getUserById(Long fileId);

    List<File> getFilePageList();
}
