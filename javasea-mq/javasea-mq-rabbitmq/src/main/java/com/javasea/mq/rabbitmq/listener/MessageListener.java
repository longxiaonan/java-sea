package com.javasea.mq.rabbitmq.listener;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;

/**
 * msg的样例
 *
 * @author longxn
 */
@Slf4j
public abstract class MessageListener implements ChannelAwareMessageListener {

    private Logger logger = LoggerFactory.getLogger(MessageListener.class);

    @Override
    public void onMessage(Message message, com.rabbitmq.client.Channel channel) throws Exception {
        byte[] body = message.getBody();
        System.out.println("MessageListener收到消息 : " + new String(body));
//        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true); //确认消息成功消费

//    	MsgPackage mp = MsgPacker.unpackone(message.getBody());
//		DataObj dataObj = DataObj.formJSONString(new String(mp.getMsgData()));
//		String devid = dataObj.getDevid();
//		log.debug("从MQ接收到车机号为:[{}]的实时数据", devid);
//		handle(dataObj);
    }

    protected abstract void handle(Object dataObj);
}
