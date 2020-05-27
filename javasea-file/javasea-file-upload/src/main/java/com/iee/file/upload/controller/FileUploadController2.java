package com.iee.file.upload.controller;

import lombok.Cleanup;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Description 通过getBytes的字节流写入到目标目录
 * @Author longxiaonan@163.com
 * @Date 23:05 2019/6/26 0026
 **/
@Controller
public class FileUploadController2 {

    @Value("${filePath}")
    private String filePath;

    @GetMapping("/upload2")
    public String uploading() {
        //跳转到 templates 目录下的 uploading.html
        return "upload";
    }

    //处理文件上传
    @PostMapping("/uploading2")
    public @ResponseBody
    String uploading(@RequestParam("file") MultipartFile file,
                     HttpServletRequest request) {
        try {
            // 读取文件
            List<String> strings = ExcelUtils.readExcel(file);
            List<String> collect = strings.stream().filter(a -> !Objects.isNull(a) && !"null".equals(a)).collect(Collectors.toList());
            // 转换成txt进行保存
            write2Txt(collect);
            //上传保存到本地
//            uploadFile(file.getBytes(), filePath, file.getOriginalFilename());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("文件上传失败!");
            return "uploading failure";
        }
        System.out.println("文件上传成功!");
        return "uploading success";
    }

    private void write2Txt(List<String> strings) throws Exception{
        BufferedWriter bw = new BufferedWriter(new FileWriter("D:\\wms\\temp2\\a.txt"));
        for (String line : strings) {
            bw.write(line);
            bw.newLine();
        }
        bw.flush();
        bw.close();
    }


    public void  uploadFile(byte[] file, String filePath, String fileName) throws Exception {
        File targetFile = new File(filePath);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        @Cleanup FileOutputStream out = new FileOutputStream(filePath+fileName);
        out.write(file);
        out.flush();
    }

}
