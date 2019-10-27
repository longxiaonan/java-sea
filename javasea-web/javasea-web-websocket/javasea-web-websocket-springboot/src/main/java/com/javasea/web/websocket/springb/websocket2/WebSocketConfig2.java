package com.javasea.web.websocket.springb.websocket2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @ClassName WebSocketConfig2
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2019/10/27 0027 20:59
 */
@Configuration
public class WebSocketConfig2 {
    /**
     * 开启@ServerEndpoint端点服务
     * 首先要注入ServerEndpointExporter类，这个bean会自动注册使用了@ServerEndpoint注解声明的Websocket endpoint。
     * 要注意，如果使用独立的servlet容器，而不是直接使用springboot的内置容器，就不要注入ServerEndpointExporter，
     * 因为 它(ServerEndpointExporter) 将由容器自己提供和管理。
     * @return
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
