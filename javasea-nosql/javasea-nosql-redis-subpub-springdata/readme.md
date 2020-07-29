### 发布
com.javasea.redis.publish.TestSenderController定时发布信息到redis
### 订阅
com.javasea.redis.subscribe.RedisMsg接口的两个实现类RedisChannelSub和RedisPmpSub会
将收到的信息打印到控制台
### 路由配置
RedisConfig2配置了listner和topic的路由，topic中的channel和TestSenderController的channel要对应

```java
@Bean
RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory) {

    RedisMessageListenerContainer container = new RedisMessageListenerContainer();
    container.setConnectionFactory(connectionFactory);
    //订阅了一个叫pmp和channel 的通道，多通道
    container.addMessageListener(listenerAdapter(new RedisPmpSub()),new PatternTopic("pmp"));
    container.addMessageListener(listenerAdapter(new RedisChannelSub()),new PatternTopic("channel"));
    container.addMessageListener(listenerAdapter(new RedisChannelSub()),new PatternTopic("flowMsgChennel"));
    //这个container 可以添加多个 messageListener
    return container;
}
```

参考：
https://blog.csdn.net/llll234/article/details/80966952
