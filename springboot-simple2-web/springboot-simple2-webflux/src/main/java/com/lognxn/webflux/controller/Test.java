package com.lognxn.webflux.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName Test
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2018/10/12 0012 14:40
 */
@RestController
@Slf4j
public class Test {
    @GetMapping("/test/{a}")
    public String test(@PathVariable String a) throws Exception{
        log.info("aaa"+a);
        Thread.sleep(10_000);
        log.info("bbb"+a);
        Thread.sleep(10_000);
        log.info("ccc"+a);
        return "success";
    }
}
