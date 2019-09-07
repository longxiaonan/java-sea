package heartMonitor.config;

import heartMonitor.cache.NettySocketHolder;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.apache.tomcat.util.net.AbstractEndpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.web.EndpointMapping;
import org.springframework.boot.actuate.endpoint.web.annotation.WebEndpoint;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/*monitor:
  channel:
    map:
      key: channelMap*/
//@WebEndpoint(id = "${monitor.channel.map.key}")//不支持${}获取application.yaml的配置文件
@WebEndpoint(id = "my-endpoint")
@Configuration
public class CustomEndpoint {

    @ReadOperation
    public Map<Long, NioSocketChannel> endpoint() {
        return NettySocketHolder.getMAP();
    }

}