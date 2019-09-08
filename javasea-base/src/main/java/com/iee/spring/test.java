package com.iee.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class test {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:/spring/spring-base.xml");
		System.out.println(123);
		// HelloBean hello = (HelloBean) context.getBean("helloBean");
		// hello.setApplicationContext(context);
		// System.out.println();
	}

}
