package com.javasea.mq.rabbitmq.controller;

import com.javasea.mq.rabbitmq.sender.HelloSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MqController {
	@Autowired
    private HelloSender helloSender;

	@GetMapping("/sender1")
	public void test1(){
		helloSender.sendDirectMsg();
	}

	@GetMapping("/sender2")
	public void test2(){
		helloSender.sendTopicMsg();
	}

	@GetMapping("/sender3")
	public void test3(){
		helloSender.sendFanoutMsg();
	}

	@GetMapping("/testSecurity")
	public void test4(){

	}
}
