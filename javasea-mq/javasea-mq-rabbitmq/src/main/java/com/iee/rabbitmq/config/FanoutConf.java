package com.iee.rabbitmq.config;

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

	@Bean(name = "Amessage")
	public Queue AMessage() {
		return new Queue("fanout.A");
	}

	@Bean(name = "Bmessage")
	public Queue BMessage() {
		return new Queue("fanout.B");
	}

	@Bean(name = "Cmessage")
	public Queue CMessage() {
		return new Queue("fanout.C");
	}

	@Bean
    FanoutExchange fanoutExchange() {
		return new FanoutExchange("fanoutExchange");// 配置广播路由器
	}

	@Bean
    Binding bindingExchangeA(@Qualifier("Amessage") Queue AMessage, FanoutExchange fanoutExchange) {
		return BindingBuilder.bind(AMessage).to(fanoutExchange);
	}

	@Bean
    Binding bindingExchangeB(@Qualifier("Bmessage") Queue BMessage, FanoutExchange fanoutExchange) {
		return BindingBuilder.bind(BMessage).to(fanoutExchange);
	}

	@Bean
    Binding bindingExchangeC(@Qualifier("Cmessage") Queue CMessage, FanoutExchange fanoutExchange) {
		return BindingBuilder.bind(CMessage).to(fanoutExchange);
	}

}
