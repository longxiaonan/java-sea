package com.javasea.mq.rabbitmq.mgr;

import com.google.common.collect.Lists;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class RabbitmqFactory {

	@Autowired
	private RabbitAdmin rabbitAdmin;

	public Queue newQueue(String queueName) {
		/*
         durable="true" 持久化 rabbitmq重启的时候不需要创建新的队列，同时实现消息持久化
         auto-delete 表示消息队列没有在使用时将被自动删除 默认是false
         exclusive  表示该消息队列是否只在当前connection生效,默认是false*/
		Queue queue = new Queue(queueName,true);
		rabbitAdmin.declareQueue(queue);
		return queue;
	}

	public List<Queue> newQueues(String ... queueName) {
		ArrayList<Queue> list = Lists.newArrayList();
		for (String q : queueName) {
			Queue queue = new Queue(q,true);
			list.add(queue);
			rabbitAdmin.declareQueue(queue);
		}
		return list;
	}

	public TopicExchange newTopicExchange(String exchange) {
		TopicExchange topicExchange = new TopicExchange(exchange,true,false);
		this.rabbitAdmin.declareExchange(topicExchange);
        return topicExchange;
    }

	public FanoutExchange newFanoutExchange(String exchange) {
		FanoutExchange fanoutExchange = new FanoutExchange(exchange);
		this.rabbitAdmin.declareExchange(fanoutExchange);
		return fanoutExchange;
	}


}
