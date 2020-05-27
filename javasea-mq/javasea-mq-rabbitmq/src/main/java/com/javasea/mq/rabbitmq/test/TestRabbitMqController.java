package com.javasea.mq.rabbitmq.test;

import com.javasea.mq.rabbitmq.entity.User;
import com.javasea.mq.rabbitmq.mgr.TypeConversionUtils;
import com.javasea.mq.rabbitmq.utils.Consumers;
import com.javasea.mq.rabbitmq.utils.Producers;
import com.javasea.mq.rabbitmq.config.RabbitmqProps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description 测试mq的组件和服务
 * @Author longxn
 * @Date 12:15 2018/7/12
 **/
@RestController
public class TestRabbitMqController {

    @Autowired
    Producers producers;

    @Autowired
    Consumers consumers;

    @Autowired
    RabbitmqProps rabbitProps;

    @GetMapping("sender")
    public void senderTest() {
        producers.sender("transferQ", "msg11111");
    }

    @GetMapping("senderObj")
    public void senderObj() {
        producers.sender("transferObjQ", new User(1, "longxiaonan", 18));
    }

    @GetMapping("receive")
    public void receiveTest() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        // 手动确认模式实现接收者,需要在Listener中ack
        executorService.submit(() -> {
            // 参数2为listener
            consumers.receiveMANUAL("transferQ", (m, c) -> {
                byte[] body = m.getBody();
                System.out.println("transferQ收到消息 : " + new String(body));
                c.basicAck(m.getMessageProperties().getDeliveryTag(), true); //确认消息成功消费
            });
        });

        executorService.submit(() -> {
            // 参数2为listener
            consumers.receiveMANUAL("transferObjQ", (m, c) -> {
                byte[] body = m.getBody();
                System.out.println("transferObjQ收到消息 : " + TypeConversionUtils.ByteToObject(body));
                c.basicAck(m.getMessageProperties().getDeliveryTag(), true); //确认消息成功消费
            });
        });

        // 手动确认模式实现接收者,需要在Listener中ack
        executorService.submit(() -> {
            // 参数2为listener
            consumers.receiveMANUAL("transferErrorQ", (m, c) -> {
                try {
                    int i = 1 / 0;
                    byte[] body = m.getBody();
                    System.out.println("transferErrorQ收到消息 : " + new String(body));
                    c.basicAck(m.getMessageProperties().getDeliveryTag(), true); //确认消息成功消费
                }catch(Exception e){
                    // 处理消息失败，将消息重新放回队列
                    c.basicNack(m.getMessageProperties().getDeliveryTag(), false,true);
                    /*
                     * 消息的标识，false只确认当前一个消息收到，true确认consumer获得的所有消息
                     * channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                     *
                     * ack返回false，并重新回到队列
                     * channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
                     *
                     * 拒绝消息
                     * channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
                     *
                     */
                }
            });
        });

        /**
         * 自动确认模式实现接收者,不需要在Listener中ack
         */
        executorService.submit(() -> {
            //transferQ值为:topic.scdata.SC.VLR_EVENT
            consumers.receiveAuto("defaultQ", (m, c) -> {
                byte[] body = m.getBody();
                System.out.println("收到消息1 : " + new String(body));
            });
        });
    }

}
