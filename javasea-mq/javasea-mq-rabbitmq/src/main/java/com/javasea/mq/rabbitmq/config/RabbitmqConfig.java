package com.javasea.mq.rabbitmq.config;

import com.javasea.mq.rabbitmq.callback.MsgSendConfirmCallBack;
import com.javasea.mq.rabbitmq.callback.MsgSendReturnCallback;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * Rabbit配置类
 * * 关于 msgSendConfirmCallBack 和 msgSendReturnCallback 的回调说明：
 * * 1.如果消息没有到exchange,则confirm回调,ack=false
 * * 2.如果消息到达exchange,则confirm回调,ack=true
 * * 3.exchange到queue成功,则不回调return
 * * 4.exchange到queue失败,则回调return(需设置mandatory=true,否则不回调,消息就丢了)
 *
 * @author longxn
 */
@Configuration
@EnableConfigurationProperties(RabbitmqProps.class)
public class RabbitmqConfig {

    @Autowired
    private RabbitmqProps rabbitmqProps;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses(rabbitmqProps.getHost() + ":" + rabbitmqProps.getPort());
        connectionFactory.setUsername(rabbitmqProps.getUsername());
        connectionFactory.setPassword(rabbitmqProps.getPassword());
        connectionFactory.setVirtualHost(rabbitmqProps.getVirtualHost());
        /** 如果要进行`发布确认`处理，则这里必须要设置为true，
         * 注意开启发布确认机制后，消息可能会被重复传递，消费者应做去重处理。 */
        connectionFactory.setPublisherConfirms(rabbitmqProps.isPublisherConfirms());
        return connectionFactory;
    }

    @Bean
    /** 因为要设置回调类，所以应是prototype类型，如果是singleton类型，则回调类为最后一次设置 */
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        /**若使用confirm-callback或return-callback，必须要配置publisherConfirms或publisherReturns为true
         * 每个rabbitTemplate只能有一个confirm-callback和return-callback*/
        template.setConfirmCallback(msgSendConfirmCallBack());
        /**
         * 使用return-callback时必须设置mandatory为true，或者在配置中设置mandatory-expression的值为true，可针对每次请求的消息去确定’mandatory’的boolean值，
         * 只能在提供’return -callback’时使用，与mandatory互斥*/
        template.setReturnCallback(msgSendReturnCallback());
        template.setMandatory(true);
        return template;
    }

    /**
     * 消息确认机制
     * Confirms给客户端一种轻量级的方式，能够跟踪哪些消息被broker处理，哪些可能因为broker宕掉或者网络失败的情况而重新发布。
     * 确认并且保证消息被送达，提供了两种方式：发布确认和事务。(两者不可同时使用)在channel为事务时，
     * 不可引入确认模式；同样channel为确认模式下，不可使用事务。
     */
    @Bean
    public MsgSendConfirmCallBack msgSendConfirmCallBack() {
        return new MsgSendConfirmCallBack();
    }

    /**
     * 消息回调机制
     *
     * @return
     */
    @Bean
    public MsgSendReturnCallback msgSendReturnCallback() {
        return new MsgSendReturnCallback();
    }


    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        return rabbitAdmin;
    }

}
