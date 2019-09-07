package com.iee.orm.mybatisplus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
@MapperScan("com.iee.orm.mybatisplus.mapper")
@SpringBootApplication
public class APPlication {
    public static void main(String[] args) {
        SpringApplication.run(APPlication.class, args);
    }

}
