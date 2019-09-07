package com.javasea.web.config.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @ClassName InfoConfig1
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2019/8/1 0001 10:35
 */
@Component
@Data
public class InfoConfig1 {

    @Value("${info.address}")
    private String address;

    @Value("${info.company}")
    private String company;

    @Value("${info.degree}")
    private String degree;
}
