package com.iee.file.io;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @ClassName FileDemo
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2019/9/8 0008 10:44
 */
public class FileDemo {
    public static void main(String[] args) throws IOException {
        FileInputStream test = FileUtils.openInputStream(new File("ApplicationLoad"));
    }
}
