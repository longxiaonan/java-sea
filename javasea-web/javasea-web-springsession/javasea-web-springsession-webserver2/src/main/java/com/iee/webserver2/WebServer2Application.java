package com.iee.webserver2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebServer2Application {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(WebServer2Application.class);
        app.run(args);
        System.err.println("sample started. http://localhost:8080/user/test");
    }

}
