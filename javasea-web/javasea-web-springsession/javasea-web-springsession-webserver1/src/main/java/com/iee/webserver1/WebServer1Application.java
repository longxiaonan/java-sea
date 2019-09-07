package com.iee.webserver1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebServer1Application {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(WebServer1Application.class);
        app.run(args);
    }

}
