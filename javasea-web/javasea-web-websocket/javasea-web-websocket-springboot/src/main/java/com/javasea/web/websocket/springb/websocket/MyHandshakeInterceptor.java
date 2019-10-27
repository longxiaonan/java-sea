package com.javasea.web.websocket.springb.websocket;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

/**
 * @Description 在握手环节的拦截，实现认证等功能
 * 和 {@link PrincipalHandshakeHandler } 的功能类似，二选一
 * @Author longxiaonan@163.com
 * @Date 21:28 2019/10/27 0027
 **/
@Component
public class MyHandshakeInterceptor implements HandshakeInterceptor {
    /** 握手之前，
     *  即客户端连接/webSocket/{INFO}时，我们可以获取到对应INFO的信息。用于认证等。
     *  若返回false，则不建立链接 */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception { //将用户id放入socket处理器的会话(WebSocketSession)中
        attributes.put("uid", 1001);
        System.out.println("开始握手。。。。。。。");
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
        System.out.println("握手成功啦。。。。。。");
    }
}
