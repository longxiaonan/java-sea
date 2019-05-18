package com.lognxn.test.controller;

import com.lognxn.test.APPlication;
import com.lognxn.test.LoadConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName TestController
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2018/10/24 0024 17:06
 */
@RestController
public class TestController {
    @Autowired
    LoadConfig loadConfig;

    @GetMapping("test")
    public void test() {
        List<String> aa = loadConfig.getAa();
        for (String a : aa) {
            System.out.println(a);
        }
    }
}
