package com.iee.es;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ElasticSearchTestApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ElasticSearchTestApplication.class);
        app.run(args);
    }

}
