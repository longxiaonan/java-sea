package com.javasea.easyexcel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @ClassName ControllerDemo
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2020/4/26 0026 22:12
 */
@Controller
public class ControllerDemo {

    @GetMapping("/")
    public String uploading() {
        //跳转到 templates 目录下的 uploading.html
        return "upload";
    }

}
