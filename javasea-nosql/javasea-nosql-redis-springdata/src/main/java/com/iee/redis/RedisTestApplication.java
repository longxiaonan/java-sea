package com.iee.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RedisTestApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(RedisTestApplication.class);
        app.run(args);
    }

}
