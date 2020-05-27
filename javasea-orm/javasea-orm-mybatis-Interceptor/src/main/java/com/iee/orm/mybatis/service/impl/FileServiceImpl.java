package com.iee.orm.mybatis.service.impl;

import com.iee.orm.mybatis.entity.File;
import com.iee.orm.mybatis.mapper.FileMapper;
import com.iee.orm.mybatis.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 文件 服务实现类
 * </p>
 *
 * @author longxiaonan
 * @since 2019-11-20
 */
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, File> implements FileService {

    @Autowired
    private FileMapper fileMapper;

    @Override
    public File getUserById(Long fileId) {
        return fileMapper.getFileById(fileId+"");
    }

    @Override
    public List<File> getFilePageList() {
        return fileMapper.getFilePageList();
    }

}
