package com.javasea.easyexcel.controller;

import com.javasea.easyexcel.entity.Teacher;
import com.javasea.easyexcel.relase.EasyExcelUtils;
import org.junit.Test;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName ImportDemo
 * @Description 关于EasyExcel的数据读取会稍微麻烦一点，直接通过工具类读取到的数据不能直接处理，需要借助一个中间的类 监听器 类
 * @Author longxiaonan@163.com
 * @Date 2020/3/23 0023 17:44
 */
@RestController
@RequestMapping("excel-import")
public class ImportDemo3 {

    @Test
    public void importTest() {

        String fileName =  "d:/wms/aaaa-无title.xls";

        EasyExcelUtils.importExcel(fileName, Teacher.class, (list) -> {
            System.out.println(list);
//            list.forEach(a -> {
//                System.out.println(a);
//            });
        });

    }

}
