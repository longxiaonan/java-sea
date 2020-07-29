package com.javasea.redis.subscribe;

public class RedisPmpSub implements RedisMsg{

    /**
     * 接收消息的方法
     * @param message 订阅消息
     */
    @Override
    public void receiveMessage(String message){
        //注意通道调用的方法名要和RedisConfig2的listenerAdapter的MessageListenerAdapter参数2相同

        System.out.println("这是RedisPmpSub"+"+++++++++++++++++"+message);
    }
}
