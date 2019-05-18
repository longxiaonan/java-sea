package heartMonitor.config;

import heartMonitor.protocol.CustomProtocol;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description 由于整合了 SpringBoot ，所以发送的心跳信息是一个单例的 Bean。
 * @Author longxiaonan@163.com
 * @Date 11:21 2018/9/25 0025
 **/
@Configuration
public class HeartBeatConfig {
    @Value("${channel.id}")
    private long id ;
    @Bean(value = "heartBeat")
    public CustomProtocol heartBeat(){
        return new CustomProtocol(id,"ping") ;
    }
}