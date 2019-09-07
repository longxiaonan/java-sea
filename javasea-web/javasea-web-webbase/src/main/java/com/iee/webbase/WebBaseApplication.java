package com.iee.webbase;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 用于展示Spring应用的最佳实践
 *
 * @author dadiyang
 * @since 2019-06-02
 */
@SpringBootApplication
@MapperScan("com.iee.webbase.**.mapper")
public class WebBaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebBaseApplication.class, args);
    }

}
