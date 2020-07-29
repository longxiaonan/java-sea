### redis发布和监听实现简单消息队列
本人在微服务项目中需要回调信息，通过rpc或者http非常麻烦，且路由需要写死不灵活。专门弄个MQ又要搭建个服务器浪费内存，就用redis发布订阅来实现了。
### 发布者
com.javasea.redis.publish.TestSenderController定时发布信息到redis
```java
/**
 * 定时器模拟消息发布者
 */
@EnableScheduling
@Component
public class TestSenderController {
    @Autowired
        private StringRedisTemplate stringRedisTemplate;

    /** 向redis消息队列index通道发布消息*/
    @Scheduled(fixedRate = 2000)
    public void sendMessage(){
        stringRedisTemplate.convertAndSend("pmp",String.valueOf(Math.random()));
        stringRedisTemplate.convertAndSend("channel",String.valueOf(Math.random()));
    }
}
```
### 订阅者
com.javasea.redis.subscribe.RedisMsg接口的两个实现类RedisChannelSub和RedisPmpSub会
将收到的信息打印到控制台
```java
public class RedisChannelSub implements RedisMsg {
    @Override
    public void receiveMessage(String message) {
        //注意通道调用的方法名要和RedisConfig2的listenerAdapter的MessageListenerAdapter参数2相同
        System.out.println("这是RedisChannelSub"+"-----"+message);
    }
}
```java
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
```
```java
/**
 * @Description 普通的消息处理器接口
 * @Author longxiaonan@163.com
 * @Date 23:50 2020/7/21 0021
 **/
@Component
public interface RedisMsg {

    public void receiveMessage(String message);
}
```
### 路由配置
RedisConfig2要配置listner和topic的路由，topic中的channel和TestSenderController的channel要对应
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
```java
 /**
     * 配置消息接收处理类
     * @param redisMsg  自定义消息接收类
     * @return
     */
    @Bean()
    @Scope("prototype")
    MessageListenerAdapter listenerAdapter(RedisMsg redisMsg) {
        //这个地方 是给messageListenerAdapter 传入一个消息接受的处理器，利用反射的方法调用“receiveMessage”
        //也有好几个重载方法，这边默认调用处理器的方法 叫handleMessage 可以自己到源码里面看
        return new MessageListenerAdapter(redisMsg, "receiveMessage");//注意2个通道调用的方法都要为receiveMessage
    }
```
### 启动程序
控制台输出：
```
2020-07-29 11:42:10.716 ERROR 11776 --- [   container-13] o.s.d.r.l.RedisMessageListenerContainer  : Connection failure occurred. Restarting subscription task after 5000 ms
这是RedisPmpSub+++++++++++++++++0.6018044162751559
这是RedisChannelSub-----0.6492059008427755
这是RedisPmpSub+++++++++++++++++0.14009953778676876
这是RedisChannelSub-----0.5201275445287328
这是RedisPmpSub+++++++++++++++++0.2196083162392929
这是RedisChannelSub-----0.3903862134377962
这是RedisPmpSub+++++++++++++++++0.5297280660628917
```
