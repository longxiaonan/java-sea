package com.iee.devtools.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName DemoController
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2019/6/11 0011 11:22
 */
@RequestMapping("/devtools")
@RestController
public class DemoController {

    @GetMapping("/test")
    public void test() {
        System.out.println("sssdf000");
    }

    @GetMapping("/test2")
    public void test2() {
        System.out.println("aab22bbcccc");
    }

}
