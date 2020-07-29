package com.javasea.redis.subscribe;

import org.springframework.stereotype.Component;

/**
 * @Description 普通的消息处理器接口
 * @Author longxiaonan@163.com
 * @Date 23:50 2020/7/21 0021
 **/
@Component
public interface RedisMsg {

    public void receiveMessage(String message);
}
