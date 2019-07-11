package com.javasea.web.log.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName DemoController
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2019/7/11 0011 13:07
 */
@Slf4j
@RestController
public class DemoController {

    @GetMapping("/test")
    public void test(){
        log.error("error test");
        log.info("info test");
        log.debug("debug test");
    }
}
