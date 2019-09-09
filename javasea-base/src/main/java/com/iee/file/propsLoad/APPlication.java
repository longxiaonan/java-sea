package com.iee.file.propsLoad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
//扫描mapper类
@ComponentScan(basePackages= {"com.iee"})//当前main方法和其下的包
//开启定时任务
@EnableScheduling
//开启异步调用方法
@EnableAsync
@EnableAutoConfiguration(exclude={
        JpaRepositoriesAutoConfiguration.class//禁止springboot自动加载持久化bean
})
public class APPlication {
    public static void main(String[] args) {
        SpringApplication.run(APPlication.class, args);
    }
}
