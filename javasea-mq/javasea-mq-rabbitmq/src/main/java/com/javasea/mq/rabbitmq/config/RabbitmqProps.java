package com.javasea.mq.rabbitmq.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * rabbitmq配置类
 * @author longxn
 */
@Data
@ConfigurationProperties(prefix = "spring.rabbitmq")
public class RabbitmqProps {
    private String host;
    private Integer port;
    private String username;
    private String password;
    private boolean publisherConfirms;
    private String virtualHost;
    //复杂类型需要使用初始化化的方式
    private Map<String,String> queueNames = new HashMap<>();


}
