package com.javasea.mq.rabbitmq.mgr;

import com.google.common.base.Splitter;
import com.javasea.mq.rabbitmq.config.RabbitmqProps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * rabbitmq工具类,通过application中rabbitmq部分动态创建和绑定队列
 * @author longxn
 */
@Component
@Slf4j
public class RabbitmqMgr {
	private RabbitAdmin rabbitAdmin;
	private RabbitmqProps rabbitProps;
	private RabbitmqFactory rabbitmqFactory;

	/**
	 * 在spring加载的时候进行初始化,对成员变量进行初始化,不可在成员变量使用@Autowired初始化,因为在bean的初始化发生在spring的@Autowired之前
	 */
	@Autowired
	public RabbitmqMgr(RabbitAdmin rabbitAdmin, RabbitmqProps rabbitProps, RabbitmqFactory rabbitmqFactory) {
		this.rabbitAdmin = rabbitAdmin;
		this.rabbitProps = rabbitProps;
		this.rabbitmqFactory = rabbitmqFactory;
	}

	/**
	 * 通过application配置文件初始化队列,在构造完成后进行初始化
	 */
	@PostConstruct
	public void init() {
		Map<String, String> queueNameMap = rabbitProps.getQueueNames();
		log.info(">>>>>>>>初始化rabbitmq");
		Collection<String> queueName = queueNameMap.values();
		for (String q : queueName) {
			List<String> queueInfo = Splitter.on(".").limit(3).trimResults().splitToList(q);
			this.binding(queueInfo.get(0), queueInfo.get(1), queueInfo.get(2), q);
		}
	}
	/**
	 * topic类型的绑定
	 *
	 * @param queue
	 * @param exchange
	 * @param routeKey
	 */
	public void bindingTopic(Queue queue, TopicExchange exchange, String routeKey) {
		rabbitAdmin.declareBinding(BindingBuilder.bind(queue).to(exchange).with(routeKey));
	}

	/**
	 * fanout类型的绑定, fanout是广播形式, 故无需绑定routeKey
	 * @param queue
	 * @param exchange
	 * @param routeKey
	 */
	public void bindingFanout(Queue queue, FanoutExchange exchange, String routeKey) {
		rabbitAdmin.declareBinding(BindingBuilder.bind(queue).to(exchange));
	}

	/**
	 * 绑定队列
	 * @param exchangeType
	 * @param exchange
	 * @param routingKey
	 * @param queueName
	 */
	public void binding(String exchangeType, String exchange, String routingKey, String queueName) {
		switch (exchangeType) {
		case ExchangeTypeConst.TOPIC:
			this.bindingTopic(rabbitmqFactory.newQueue(queueName), rabbitmqFactory.newTopicExchange(exchange),
					routingKey);
			break;
		case ExchangeTypeConst.FANOUT:
			this.bindingFanout(rabbitmqFactory.newQueue(queueName), rabbitmqFactory.newFanoutExchange(exchange),
					routingKey);
			break;
			// direct无需绑定
		case ExchangeTypeConst.DIRECT:
			break;
		}
	}

}
