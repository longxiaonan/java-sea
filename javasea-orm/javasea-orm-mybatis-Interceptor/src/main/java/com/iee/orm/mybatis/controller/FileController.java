package com.iee.orm.mybatis.controller;


import com.iee.orm.mybatis.entity.File;
import com.iee.orm.mybatis.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 文件 前端控制器
 * </p>
 *
 * @author longxiaonan
 * @since 2019-11-20
 */
@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileService fileService;

    @GetMapping("/{fileId}")
    public File getFileById(@PathVariable Long fileId){
        File file = fileService.getUserById(fileId);
        return file;
    }

    @GetMapping("/list")
    public List<File> getFilePageList(){
        List<File> fileList = fileService.getFilePageList();
        return fileList;
    }

    @GetMapping("save")
    public void save(File file){
        this.fileService.save(file);
    }
}

