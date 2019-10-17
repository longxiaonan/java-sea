package com.file.openApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 用于展示Spring应用的最佳实践
 * @author longxiaonan@163.com
 * @since 2019-06-02
 */
@SpringBootApplication
//@EnableDiscoveryClient
public class Application {

    public static void main(String[] args) {
        // 启动spring-boot-plus
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        // 打印项目信息
    }

}
