package com.javasea.web.websocket.springb.quickstart;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint("/ws/{uid}")
public class MyWebSocket {
    @OnOpen
    public void onOpen(Session session, @PathParam("uid") String uid) throws
            IOException {
        // 连接成功
        session.getBasicRemote().sendText(uid + "，你好，欢迎连接WebSocket！");
    }

    @OnClose
    public void onClose() {
        System.out.println(this + "关闭连接");
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        System.out.println("接收到消息：" + message);
        session.getBasicRemote().sendText("消息已收到.");
    }

    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }
}
