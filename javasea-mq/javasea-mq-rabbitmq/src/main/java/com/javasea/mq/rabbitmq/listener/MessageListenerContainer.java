package com.javasea.mq.rabbitmq.listener;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

/**
 * @author longxiaonan@aliyun.com
 */
@Component
public class MessageListenerContainer {
	/**
	 * MessageListenerContainer 观察者模式进行监听 当有消息到达时会通知监听在对应的队列上的监听对象
	 */
	public SimpleMessageListenerContainer newRabbitListenerContainer(ConnectionFactory connectionFactory, Queue queue,
                                                                     AcknowledgeMode acknowledgeMode, ChannelAwareMessageListener listener) {
		SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer(
				connectionFactory);
		//添加队列到容器中
		simpleMessageListenerContainer.addQueues(queue);
		simpleMessageListenerContainer.setExposeListenerChannel(true);
		//最大并发消费者数量
		simpleMessageListenerContainer.setMaxConcurrentConsumers(2);
		//指定并发消费者的数量
		simpleMessageListenerContainer.setConcurrentConsumers(2);
		simpleMessageListenerContainer.setAcknowledgeMode(acknowledgeMode);
		simpleMessageListenerContainer.setMessageListener(listener);
		return simpleMessageListenerContainer;
	}
}
