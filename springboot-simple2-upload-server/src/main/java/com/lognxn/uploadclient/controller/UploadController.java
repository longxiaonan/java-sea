package com.lognxn.uploadclient.controller;

import com.lognxn.uploadclient.dto.FileInfo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;

/**
 * @ClassName UploadController
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2018/8/12 19:15
 */
@RestController
public class UploadController {

//    private String folder = "C:/tmp/images";
    private String folder = "/tmp/images";
    /**
     *
     * @param file
     *     传入的file对象, 参考测试用例中的whenUploadSuccess方法
     * @return
     * @throws IllegalStateException
     */
    @PostMapping("/file-upload")
    public FileInfo upload(MultipartFile file) throws Exception {
        System.out.println(file.getName());
        System.out.println(file.getOriginalFilename());
        System.out.println(file.getSize());
        System.out.println(file.getOriginalFilename());
        System.out.println(file.getContentType());
        String[] split = file.getOriginalFilename().split("\\.");
        System.out.println(split[0]);
        System.out.println(split[1]);
        File localFile = new File(folder, new Date().getTime() + "."+split[1]);
        file.transferTo(localFile);
        return new FileInfo(localFile.getAbsolutePath());
    }
}
