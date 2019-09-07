package com.javasea.web.config.controller;

import com.javasea.web.config.config.InfoConfig1;
import com.javasea.web.config.config.LoadConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName DemoController
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2019/8/1 0001 22:36
 */
@RestController
@RequestMapping("/config")
@Slf4j
public class DemoController {

    @Autowired
    LoadConfig loadConfig;

    @Autowired
    InfoConfig1 infoConfig1;

    @GetMapping("/test")
    public List<String> test(){
        System.out.println(555);
        return loadConfig.getAa();
    }

    @GetMapping("/test2")
    public InfoConfig1 test2(){
        log.info("" ,infoConfig1);
        return infoConfig1;
    }
}
