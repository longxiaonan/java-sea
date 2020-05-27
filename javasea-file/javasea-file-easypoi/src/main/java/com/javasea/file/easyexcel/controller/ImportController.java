package com.javasea.file.easyexcel.controller;

import com.javasea.file.easyexcel.entity.Teacher;
import com.javasea.file.easyexcel.utils.EasyPoiUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.InputStream;

/**
 * @ClassName ImportController
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2020/3/6 0006 11:23
 */
@Slf4j
@Controller
public class ImportController {

    @GetMapping("/")
    public String uploading() {
        //跳转到 templates 目录下的 uploading.html
        return "upload";
    }

    @PostMapping("/uploading")
    public @ResponseBody
    String uploading(@RequestParam("file") MultipartFile file,
                     HttpServletRequest request) throws Exception {
        log.info(file.getOriginalFilename(), file.getName(), file.getSize());

        InputStream inputStream = file.getInputStream();
        EasyPoiUtils.importExcel(inputStream, 1, 1 , Teacher.class);
        File localFile = new File("D:/wms", System.currentTimeMillis()+file.getOriginalFilename());
        file.transferTo(localFile);
        return localFile.getAbsolutePath();
    }
}
