package com.javasea.web.junit.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName HelloController
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2019/7/28 0028 0:54
 */
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String sayHello(){
        System.out.println("HelloController");
        return "hello";
    }
}
