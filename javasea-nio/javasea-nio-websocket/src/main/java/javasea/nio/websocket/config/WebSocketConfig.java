package javasea.nio.websocket.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;


/**
 * WebSocketConfig配置
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Autowired
    private WebSocketDecoratorFactory webSocketDecoratorFactory;
    @Autowired
    private PrincipalHandshakeHandler principalHandshakeHandler;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        /**
         * myUrl表示 你前端到时要对应url映射
         */
        registry.addEndpoint("/myUrl")
                //允许跨域连接，否则前后端分离的方式的时候连接失败
                .setAllowedOrigins("*")
                //添加
                .setHandshakeHandler(principalHandshakeHandler)
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        /**
         * queue 点对点
         * topic 广播
         * user 点对点前缀
         */
        registry.enableSimpleBroker("/queue", "/topic");
        registry.setUserDestinationPrefix("/user");
    }

//    @Override
//    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
//        registration.addDecoratorFactory(webSocketDecoratorFactory);
//        super.configureWebSocketTransport(registration);
//    }
}


