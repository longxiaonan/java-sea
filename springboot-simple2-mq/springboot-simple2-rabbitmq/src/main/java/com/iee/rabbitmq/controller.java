package com.iee.rabbitmq;

import com.alibaba.fastjson.JSON;
import com.iee.common.entity.User;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

/**
 * @ClassName controller
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
