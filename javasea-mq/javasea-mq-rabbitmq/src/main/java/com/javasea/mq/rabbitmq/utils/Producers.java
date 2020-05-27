package com.javasea.mq.rabbitmq.utils;

import com.google.common.base.Splitter;
import com.javasea.mq.rabbitmq.config.RabbitmqProps;
import com.javasea.mq.rabbitmq.mgr.ExchangeTypeConst;
import com.javasea.mq.rabbitmq.mgr.TypeConversionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 生产者工具类
 *
 * @author longxn
 */
@Component
public class Producers {

    private RabbitmqProps rabbitProps;
    private RabbitTemplate rabbitTemplate;

    @Autowired
    public Producers(RabbitmqProps rabbitProps, RabbitTemplate rabbitTemplate) {
        this.rabbitProps = rabbitProps;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sender(String queueKey, Object msg) { //queueKey: transferQ, 对应的转发规则:topic.scdata.SC.VLR_EVENT
        Map<String, String> queueNameMap = rabbitProps.getQueueNames();
        String uuid = UUID.randomUUID().toString(); //消息id
        String q = MapUtils.getString(queueNameMap, queueKey); //topic.scdata.SC.VLR_EVENT
        List<String> queueInfo = Splitter.on(".").limit(3).trimResults().splitToList(q);
        CorrelationData correlationId = new CorrelationData(uuid);
        senderByType(queueInfo.get(0), queueInfo.get(1), queueInfo.get(2), msg, correlationId);
    }

    public void senderByType(String exchangeType, String exchange, String routingKey, Object msg, CorrelationData correlationId) {
        switch (exchangeType) {
            case ExchangeTypeConst.TOPIC:
                sendTopicMsg(exchange, routingKey, msg, correlationId);
                break;
            case ExchangeTypeConst.FANOUT:
                sendFanoutMsg(exchange, "", msg, correlationId);
                break;
            case ExchangeTypeConst.DIRECT:// direct无需绑定
                sendDirectMsg(routingKey, msg, correlationId);
                break;
        }
    }

    private void sendDirectMsg(String routingKey, Object msg, CorrelationData correlationId) {
        //发生消息,direct模式采用默认的exchange
        rabbitTemplate.convertAndSend(routingKey, msg, correlationId);
    }

    /** 消息类型是 String */
    private void sendTopicMsg(String exchange, String routingKey, Object msg, CorrelationData correlationId) {
        //发送消息, exchange是"exchange"且route key是"topic.msgqueue"或者"topic.*"或者topic.#"的能接收到消息
		rabbitTemplate.convertAndSend(exchange, routingKey, msg, correlationId);
		/** TODO 存入到缓存，用于在发送失败的时候进行重发
         *  当 MsgSendReturnCallback回调时（消息从交换机到队列失败）,进行处理 {@code MsgSendReturnCallback}.
         *  * 当 MsgSendConfirmCallBack回调时,进行处理 {@code MsgSendConfirmCallBack}.
         *  * 定时检查这个绑定关系列表,如果发现一些已经超时(自己设定的超时时间)未被处理,则手动处理这些消息.
         * */

    }

    /** 消息类型是 实体对象 */
    private void sendTopicObjMsg(String exchange, String routingKey, Object msg, CorrelationData correlationId) {
        Message message = MessageBuilder.withBody(TypeConversionUtils.ObjectToByte(msg))
                .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                .setCorrelationId(correlationId.getId()).build();
        //发送消息, exchange是"exchange"且route key是"topic.msgqueue"或者"topic.*"或者topic.#"的能接收到消息
        rabbitTemplate.convertAndSend(exchange, routingKey, message, correlationId);
    }

    private void sendFanoutMsg(String exchange, String routingKey, Object msg, CorrelationData correlationId) {
        //发送消息, route key可以设置为"",因为是Fanout模式,所以没有意义,就算配置也会被忽略
        rabbitTemplate.convertAndSend(exchange, routingKey, msg, correlationId);
    }
}
