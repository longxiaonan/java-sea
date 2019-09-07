package com.github.dadiyang.nettycs.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author dadiyang
 */
@Slf4j
@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages = "com.github.dadiyang.nettycs")
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }
}
