package com.javasea.mq.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Fanout 模式配置
 * Fanout Exchange形式又叫广播形式,因此我们发送到路由器的消息  会使得绑定到该路由器的每一个Queue接收到消息,这个时候就算指定了Key,或者规则(
 * 即上文中convertAndSend方法的参数2),也会被忽略!那么直接上代码
 * @author longxn
 */
@Configuration
//@EnableRabbit
public class FanoutConf {

	@Bean(name = "Aqueue")
	public Queue AMessage() {
		return new Queue("fanout.A");
	}

	@Bean(name = "Bqueue")
	public Queue BMessage() {
		return new Queue("fanout.B");
	}

	@Bean(name = "Cqueue")
	public Queue CMessage() {
		return new Queue("fanout.C");
	}

	@Bean
    FanoutExchange fanoutExchange() {
		return new FanoutExchange("fanoutExchange");// 配置广播路由器
	}

	@Bean
    Binding bindingExchangeA(@Qualifier("Aqueue") Queue Aqueue, FanoutExchange fanoutExchange) {
		return BindingBuilder.bind(Aqueue).to(fanoutExchange);
	}

	@Bean
    Binding bindingExchangeB(@Qualifier("Bqueue") Queue Bqueue, FanoutExchange fanoutExchange) {
		return BindingBuilder.bind(Bqueue).to(fanoutExchange);
	}

	@Bean
    Binding bindingExchangeC(@Qualifier("Cqueue") Queue Cqueue, FanoutExchange fanoutExchange) {
		return BindingBuilder.bind(Cqueue).to(fanoutExchange);
	}

}
