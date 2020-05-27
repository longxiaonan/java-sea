package com.javasea.easyexcel.controller;

import com.javasea.easyexcel.entity.ProductSpecification;
import com.javasea.easyexcel.entity.Teacher;
import com.javasea.easyexcel.relase.EasyExcelUtils;
import com.javasea.easyexcel.relase.executor.HandeSheetExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ImportDemo
 * @Description 多个sheet页读取
 * @Author longxiaonan@163.com
 * @Date 2020/3/23 0023 17:44
 */
@Controller
public class ImportMultiSheetDemo5 {

    /** 读取多个sheet页 */
    @PostMapping("/uploading5")
    public @ResponseBody void uploading(@RequestParam("file") MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        System.out.println(originalFilename);
        // sheetName 是 某个sheet的名称，作为一个类型进行存储，list是sheetName下面的数据
        HandeSheetExecutor sheetExecutor = (list, sheetName) -> {
            // 编写业务逻辑
            System.out.println(sheetName);
            list.forEach(a -> {
                System.out.println(a);
            });
        };
        // 需要校验的表头
        Map<Integer, String> headNameMap = new HashMap<>();
        headNameMap.put(0, "老师姓名1");
        headNameMap.put(1, "头像地址1");
        EasyExcelUtils.importExcel(file, Teacher.class, 1, headNameMap, sheetExecutor);
    }

    /** 只读取第一个sheet页 */
    @PostMapping("/uploading51")
    public @ResponseBody void uploading2(@RequestParam("file") MultipartFile file) throws IOException {
        // 需要校验的表头
        Map<Integer, String> headNameMap = new HashMap<>();
        headNameMap.put(0, "老师姓名1");
        headNameMap.put(1, "头像地址1");
        EasyExcelUtils.importExcel(file, Teacher.class, 1, headNameMap, (list) -> {
            // 编写业务逻辑
            System.out.println(list);
        });

    }

}
