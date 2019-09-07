package com.javasea.web.config.config.outside;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @ClassName DBConfig2
 * @Description @PropertySource+@ConfigurationProperties注解读取方式, @PropertySource不支持yml文件读取。
 * @Author longxiaonan@163.com
 * @Date 2019/8/1 0001 10:43
 */
@Data
@Component
@ConfigurationProperties(prefix = "db")
@PropertySource(value = "classpath:config/db-config.properties")
public class DBConfig2 {
    private String username;
    private String password;
}
