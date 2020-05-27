package com.javasea.mq.rabbitmq.callback;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * 发送成功后回调处理类，发布到rabbitmq后ack就为true, 不管有没消费者。
 * 如果发送发送失败需要重新发送, 消费者端可能会受到重复消息，需要去重处理，功能
 * 需要在{@link com.javasea.mq.rabbitmq.config.RabbitmqConfig 中开启}
 * @author longxiaonan@aliyun.com
 *
 */
@Slf4j
public class MsgSendConfirmCallBack implements RabbitTemplate.ConfirmCallback{
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) {
            log.debug("消息成功发送,消息id为[{}]", correlationData.getId());
            // TODO 删除 msgId 与 Message 的关系
        } else {
            log.error("消息成功发送,消息id为[{}],失败原因为[{}],需要重新发送",cause);
            // TODO 消息发送到exchange失败 ， 重新发送
        }
    }
}
