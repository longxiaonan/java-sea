package com.github.dadiyang.nettycs.client;

import com.github.dadiyang.nettycs.client.handler.EchoClientPacketHandler;
import com.github.dadiyang.nettycs.core.codec.PacketDecoder;
import com.github.dadiyang.nettycs.core.codec.PacketEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

/**
 * 客户端启动器
 *
 * @author dadiyang
 * @date 2019/1/21
 */
@Slf4j
@Component
@Setter
@ConfigurationProperties(prefix = "nettycs.server")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ClientRunner implements CommandLineRunner, DisposableBean {
    private boolean running;
    private String host;
    private int port;

    @Override
    public void run(String... args) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        try {
            log.info("客户端启动");
            running = true;
            bootstrap.group(group)
                    // 使用指定地址和端口连接服务器
                    .remoteAddress(new InetSocketAddress(host, port))
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ch.pipeline().addLast("packetEncoder", new PacketEncoder());
                            ch.pipeline().addLast("packetDecoder", new PacketDecoder());
                            ch.pipeline().addLast("clientHandler", new EchoClientPacketHandler());
                        }
                    });
            int failTime = 0;
            while (running) {
                try {
                    ChannelFuture future = bootstrap.connect().sync();
                    // 此处连接之后阻塞直到断开连接
                    future.channel().closeFuture().sync();
                } catch (Exception e) {
                    log.error("客户端连接发生异常" + e.getMessage());
                }
                // 如果连接断开，则尝试重连
                failTime++;
                // 根据失败次数决定休眠时长
                long sleepTime = Math.min(failTime * 5_000L, 120_000L);
                log.info("准备重连，sleepTime=" + sleepTime);
                Thread.sleep(sleepTime);
            }
        } finally {
            group.shutdownGracefully();
        }
        log.warn("客户端退出");
    }

    @Override
    public void destroy() {
        running = false;
    }
}
