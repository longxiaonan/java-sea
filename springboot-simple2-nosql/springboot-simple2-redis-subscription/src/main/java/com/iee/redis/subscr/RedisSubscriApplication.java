package com.iee.redis.subscr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RedisSubscriApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(RedisSubscriApplication.class);
        app.run(args);
    }

}
