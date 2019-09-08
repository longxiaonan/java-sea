package com.iee.rabbitmq.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Direct模式,需要在配置Queue的时候,指定一个route key,使其和交换机绑定.
 * @author longxn
 */
@Configuration
//@EnableRabbit
public class DirectConf {
     @Bean
     public Queue queue() {
          return new Queue("queue");
     }
}
