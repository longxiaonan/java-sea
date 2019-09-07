package com.javasea.web.config.config.outside;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @ClassName DBConfig1
 * @Description @PropertySource+@Value注解读取方式, @PropertySource不支持yml文件读取。
 * @Author longxiaonan@163.com
 * @Date 2019/8/1 0001 10:40
 */
@Data
@Component
@PropertySource(value = "classpath:config/db-config.properties")
public class DBConfig1 {
    @Value("${db.username}")
    private String username;
    @Value("${db.password}")
    private String password;

}
