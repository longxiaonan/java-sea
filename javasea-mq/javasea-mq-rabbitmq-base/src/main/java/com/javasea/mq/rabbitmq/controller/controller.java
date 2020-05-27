package com.javasea.mq.rabbitmq.controller;

import com.alibaba.fastjson.JSON;
import com.javasea.mq.rabbitmq.entity.User;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

/**
 * @ClassName com.iee.templates.modelConvertDemo
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2018/9/10 0010 21:00
 */
public class controller implements Serializable {
    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send(User user, String s) {
        String content = JSON.toJSONString(user);
        this.rabbitTemplate.convertAndSend("stone", content);
    }
}
