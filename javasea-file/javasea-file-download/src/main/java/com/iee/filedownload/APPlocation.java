package com.iee.filedownload;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.iee.mgr","com.iee.filedownload"})
public class APPlocation {
    public static void main(String[] args) {
        SpringApplication.run(APPlocation.class, args);
    }
}
