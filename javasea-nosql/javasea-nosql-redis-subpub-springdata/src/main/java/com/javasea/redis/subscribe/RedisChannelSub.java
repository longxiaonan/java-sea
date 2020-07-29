package com.javasea.redis.subscribe;

public class RedisChannelSub implements RedisMsg {
    @Override
    public void receiveMessage(String message) {
        //注意通道调用的方法名要和RedisConfig2的listenerAdapter的MessageListenerAdapter参数2相同
        System.out.println("这是RedisChannelSub"+"-----"+message);
    }
}
