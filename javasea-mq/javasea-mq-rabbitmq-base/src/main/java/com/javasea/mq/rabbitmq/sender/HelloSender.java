package com.javasea.mq.rabbitmq.sender;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HelloSender {
	@Autowired
	private AmqpTemplate template;

	public void sendDirectMsg() {
		//发生消息,direct模式采用默认的exchange, fault_XXX
		template.convertAndSend("queue", "hello,rabbit~");
	}

	public void sendTopicMsg(){
		//发送消息, exchange是"exchange"且route key是"topic.message"或者"topic.*"或者topic.#"的能接收到消息
		template.convertAndSend("exchange", "topic.message", "hello,rabbit");
	}

	public void sendFanoutMsg(){
		//发送消息, route key是设置为"",因为是Fanout模式,所以没有意义,就算配置也会被忽略
		template.convertAndSend("fanoutExchange", "", "hello,rabbit");
	}
}
