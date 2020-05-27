package com.javasea.mq.rabbitmq.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class HelloReceiver {
	@RabbitListener(queues = "queue") // 监听器监听指定的Queue名称, 交换机默认是default_XXX
	public void process(String str) {
		System.out.println("Receive:" + str);
	}

	@RabbitListener(queues = "topic.message") // 监听器监听指定的Queue
	public void process1(String str) {
		System.out.println("message1:" + str);
	}

	@RabbitListener(queues = "topic.messages") // 监听器监听指定的Queue
	public void process2(String str) {
		System.out.println("message2:" + str);
	}

	@RabbitListener(queues = "fanout.A")
	public void processA(String str1) {
		System.out.println("ReceiveA:" + str1);
	}

	@RabbitListener(queues = "fanout.B")
	public void processB(String str) {
		System.out.println("ReceiveB:" + str);
	}

	@RabbitListener(queues = "fanout.C")
	public void processC(String str) {
		System.out.println("ReceiveC:" + str);
	}
}
