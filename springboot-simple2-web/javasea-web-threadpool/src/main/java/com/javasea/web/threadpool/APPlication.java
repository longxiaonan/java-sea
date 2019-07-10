package com.javasea.web.threadpool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class APPlication {
    public static void main(String[] args) {
        SpringApplication.run(APPlication.class, args);
    }
}
