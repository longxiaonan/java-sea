package com.iee.file.upload.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * @ClassName FileUpload2Controller
 * @Description 通过“file.transferTo”将文件写入到本地
 * @Author longxiaonan@163.com
 * @Date 2019/6/26 0026 22:58
 */
@RestController
@RequestMapping("/file2")
public class FileUpload2LocalController {
    @Value("${filePath}")
    private String filePath;

    @GetMapping("/upload")
    public String uploading() {
        //跳转到 templates 目录下的 uploading.html
        return "upload";
    }

    //处理文件上传
    @PostMapping("/uploading")
    public @ResponseBody
    String uploading(@RequestParam("file") MultipartFile file,
                     HttpServletRequest request) throws IOException {
        System.out.println(file.getOriginalFilename());
        System.out.println(file.getName());
        System.out.println(file.getSize());

        File localFile = new File(filePath, System.currentTimeMillis()+file.getOriginalFilename());

        file.transferTo(localFile);
        return localFile.getAbsolutePath();
    }
}
