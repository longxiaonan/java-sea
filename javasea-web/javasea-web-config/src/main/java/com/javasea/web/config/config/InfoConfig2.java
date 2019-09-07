package com.javasea.web.config.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ClassName InfoConfig2
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2019/8/1 0001 10:38
 */
@Component
@ConfigurationProperties(prefix = "info")
public class InfoConfig2 {

    private String address;

    private String company;

    private String degree;
}
