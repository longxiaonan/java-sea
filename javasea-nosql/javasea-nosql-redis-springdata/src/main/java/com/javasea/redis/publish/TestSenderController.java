package com.javasea.redis.publish;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javasea.redis.entity.FlowMsgDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 定时器模拟消息发布者
 */
@EnableScheduling
@RestController
public class TestSenderController {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    /** 向redis消息队列index通道发布消息*/
//    @Scheduled(fixedRate = 2000)
//    public void sendMessage(){
//        stringRedisTemplate.convertAndSend("pmp",String.valueOf(Math.random()));
//        stringRedisTemplate.convertAndSend("channel",String.valueOf(Math.random()));
//    }
    @GetMapping("sendMsg")
    public void sendMessage(FlowMsgDTO flowMsgDTO) throws JsonProcessingException {

        String flowMsgDTOStr = objectMapper.writeValueAsString(flowMsgDTO);

        stringRedisTemplate.convertAndSend("flowMsgChennel",flowMsgDTOStr);
    }
}
