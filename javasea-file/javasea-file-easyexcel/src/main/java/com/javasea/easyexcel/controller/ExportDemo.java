package com.javasea.easyexcel.controller;

import com.alibaba.excel.EasyExcel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javasea.easyexcel.entity.Teacher;
import com.javasea.easyexcel.relase.EasyExcelUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ExportDemo
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2020/3/23 0023 17:28
 */
@RestController
public class ExportDemo {

    @Autowired
    ObjectMapper objectMapper;

    /**
     * 基本的导出
     */
    @Test
    public void exportTest() {
        //        准备数据
        List<Teacher> teachers1 = new ArrayList<>();
        teachers1.add(new Teacher(1, "a1", "hhh.jpg", 1));
        teachers1.add(new Teacher(1, "a2", "hhh.jpg", 1));
        teachers1.add(new Teacher(1, "a3", "hhh.jpg", 1));
        teachers1.add(new Teacher(1, "a4", "hhh.jpg", 1));
        teachers1.add(new Teacher(1, "a5", "hhh.jpg", 1));
        teachers1.add(new Teacher(1, "a6", "hhh.jpg", 1));
        String fileName = "d:/wms/aaaa.xls";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        EasyExcel.write(fileName, Teacher.class).sheet("sheet1").doWrite(teachers1);
    }

    /**
     * 导出数据
     */
    @GetMapping("downloadFailedUsingJson")
    public void downloadFailedUsingJson(HttpServletResponse response) throws IOException {

        List<Teacher> teachers1 = new ArrayList<>();
        teachers1.add(new Teacher(1, "a1", "hhh.jpg", 1));
        teachers1.add(new Teacher(1, "a2", "hhh.jpg", 1));
        teachers1.add(new Teacher(1, "a3", "hhh.jpg", 1));
        teachers1.add(new Teacher(1, "a4", "hhh.jpg", 1));
        teachers1.add(new Teacher(1, "a5", "hhh.jpg", 1));
        teachers1.add(new Teacher(1, "a6", "hhh.jpg", 1));

        EasyExcelUtils.exportExcel("测试", "模板", Teacher.class, teachers1, response);
    }

}
