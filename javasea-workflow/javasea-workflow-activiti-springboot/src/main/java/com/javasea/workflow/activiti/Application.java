package com.javasea.workflow.activiti;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

/**
 * @Description 排除掉去扫描 Security配置，那样就在controller中无需去配置security执行登陆了
 * @Author longxiaonan@163.com
 * @Date 22:03 2019/9/19 0019
 **/
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class Application {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        app.run(args);
    }

}
