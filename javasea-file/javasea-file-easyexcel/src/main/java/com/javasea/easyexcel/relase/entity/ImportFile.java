package com.javasea.easyexcel.relase.entity;

import lombok.Data;

import java.io.InputStream;

/**
 * @ClassName ImportFile
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2020/4/1 0001 10:17
 */
@Data
public class ImportFile {
    private String filename;
    private String originalFilename;
    private Integer fileSize;
    private InputStream inputStream;
    private String importUserId;
    private String importUserName;
}
