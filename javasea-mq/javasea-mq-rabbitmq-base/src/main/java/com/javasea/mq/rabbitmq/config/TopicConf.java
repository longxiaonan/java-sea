package com.javasea.mq.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 需要配置队列Queue,再配置交换机(Exchange),再把队列按照相应的规则绑定到交换机上
 *
 * @author longxn
 */
@Configuration
//@EnableRabbit
public class TopicConf {

    @Bean(name = "message")
    public Queue queueMessage() {
        return new Queue("topic.message");
    }

    @Bean(name = "messages")
    public Queue queueMessages() {
        return new Queue("topic.messages");
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange("exchange");
    }

    /**
     * @Qualifier通过名称选择注入bean
     * `topic.messages`队列接收routingKey是`topic.message`的消息
     */
    @Bean
    Binding bindingExchangeMessage(@Qualifier("message") Queue queueMessage, TopicExchange exchange) {
        return BindingBuilder.bind(queueMessage).to(exchange).with("topic.message");
    }

    /**
     * `topic.messages`队列接收的是routingKey是`topic`开始的所有消息。*表示一个词,#表示零个或多个词
     */
    @Bean
    Binding bindingExchangeMessages(@Qualifier("messages") Queue queueMessages, TopicExchange exchange) {
        return BindingBuilder.bind(queueMessages).to(exchange).with("topic.#");
    }
}
