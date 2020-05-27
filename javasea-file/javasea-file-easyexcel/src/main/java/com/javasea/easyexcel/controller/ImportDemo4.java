package com.javasea.easyexcel.controller;

import com.javasea.easyexcel.entity.Teacher;
import com.javasea.easyexcel.relase.EasyExcelUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @ClassName ImportDemo
 * @Description 关于EasyExcel的数据读取会稍微麻烦一点，直接通过工具类读取到的数据不能直接处理，需要借助一个中间的类 监听器 类
 * @Author longxiaonan@163.com
 * @Date 2020/3/23 0023 17:44
 */
@Controller
public class ImportDemo4 {
    @PostMapping("/uploading")
    public @ResponseBody void uploading(@RequestParam("file") MultipartFile file) throws IOException {

        EasyExcelUtils.importExcel(file, Teacher.class, 2, (list) -> {
            // 编写存储逻辑
            list.forEach(a -> {
                System.out.println(a);
            });
        });

    }

}
