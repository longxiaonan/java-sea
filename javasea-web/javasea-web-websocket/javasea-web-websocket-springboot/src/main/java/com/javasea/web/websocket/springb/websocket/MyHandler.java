package com.javasea.web.websocket.springb.websocket;

import com.javasea.web.websocket.springb.websocket2.WebSocketServer;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @Description 在Spring中，处理消息的具体业务逻辑需要实现WebSocketHandler接口。
 * @Author longxiaonan@163.com
 * @Date 16:50 2019/10/27 0027
 **/
public class MyHandler extends TextWebSocketHandler {

    private static Map<String, WebSocketSession> webSocketSessionMap = new ConcurrentHashMap();

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        System.out.println("获取到消息 >> " + message.getPayload());
        session.sendMessage(new TextMessage("消息已收到"));
        if (message.getPayload().equals("10")) {
            for (int i = 0; i < 10; i++) {
                //回写消息到client
                session.sendMessage(new TextMessage("消息 -> " + i));
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String uid = (String)session.getAttributes().get("uid");
        //可以保存 session 对象来发送消息
        webSocketSessionMap.put(uid, session);
        System.out.println("uid =>" + session.getAttributes().get("uid"));
        session.sendMessage(new TextMessage("欢迎连接到ws服务"));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("断开连接！");
    }
}
