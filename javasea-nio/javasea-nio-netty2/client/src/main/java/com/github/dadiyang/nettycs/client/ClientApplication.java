package com.github.dadiyang.nettycs.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 客户端
 *
 * @author dadiyang
 * @date 2019/1/21
 */
@Slf4j
@SpringBootApplication
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@ComponentScan(basePackages = "com.github.dadiyang.nettycs")
public class ClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }
}
