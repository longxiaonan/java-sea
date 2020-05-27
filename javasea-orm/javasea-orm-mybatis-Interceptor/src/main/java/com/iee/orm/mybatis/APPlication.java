package com.iee.orm.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author longxiaonan@aliyun.com
 */
@MapperScan("com.iee.orm.mybatis.mapper")
@SpringBootApplication
@ComponentScan("com.iee.orm.mybatis")
public class APPlication {

    public static void main(String[] args) {
        SpringApplication.run(APPlication.class, args);
    }

}
