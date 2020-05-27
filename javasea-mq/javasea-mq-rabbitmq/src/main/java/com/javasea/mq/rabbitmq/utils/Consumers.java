package com.javasea.mq.rabbitmq.utils;

import com.javasea.mq.rabbitmq.config.RabbitmqProps;
import com.javasea.mq.rabbitmq.listener.MessageListenerContainer;
import com.javasea.mq.rabbitmq.mgr.RabbitmqFactory;
import org.apache.commons.collections4.MapUtils;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Consumers {
    @Autowired
    MessageListenerContainer container;
    @Autowired
    ConnectionFactory connectionFactory;
    @Autowired
    RabbitmqFactory rabbitmqFactory;
    @Autowired
    RabbitmqProps rabbitProps;

    public void receiveNONE(String queueKey, ChannelAwareMessageListener listener) {
        String queueName = MapUtils.getString(rabbitProps.getQueueNames(), queueKey);
        SimpleMessageListenerContainer listenerContainer = container.newRabbitListenerContainer(connectionFactory, rabbitmqFactory.newQueue(queueName), AcknowledgeMode.NONE, listener);
        listenerContainer.start();
    }

    public void receiveAuto(String queueKey, ChannelAwareMessageListener listener) {
        String queueName = MapUtils.getString(rabbitProps.getQueueNames(), queueKey);
        SimpleMessageListenerContainer listenerContainer = container.newRabbitListenerContainer(connectionFactory, rabbitmqFactory.newQueue(queueName), AcknowledgeMode.AUTO, listener);
        listenerContainer.start();
    }

    /** queueKey: topic.scdata.SC.VLR_EVENT */
    public void receiveMANUAL(String queueKey, ChannelAwareMessageListener listener) {
        String queueName = MapUtils.getString(rabbitProps.getQueueNames(), queueKey);
        SimpleMessageListenerContainer listenerContainer = container.newRabbitListenerContainer(connectionFactory, rabbitmqFactory.newQueue(queueName), AcknowledgeMode.MANUAL, listener);
        listenerContainer.start();
    }

    public void receive(String queueKey, AcknowledgeMode acknowledgeMode, ChannelAwareMessageListener listener) {
        String queueName = MapUtils.getString(rabbitProps.getQueueNames(), queueKey);
        SimpleMessageListenerContainer listenerContainer = container.newRabbitListenerContainer(connectionFactory, rabbitmqFactory.newQueue(queueName), acknowledgeMode, listener);
        listenerContainer.start();
    }
}
