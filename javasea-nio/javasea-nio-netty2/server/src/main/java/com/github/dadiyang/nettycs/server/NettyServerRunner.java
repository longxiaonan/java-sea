package com.github.dadiyang.nettycs.server;

import com.github.dadiyang.nettycs.core.codec.PacketDecoder;
import com.github.dadiyang.nettycs.core.codec.PacketEncoder;
import com.github.dadiyang.nettycs.server.handler.ChannelIdleHandler;
import com.github.dadiyang.nettycs.server.handler.EchoPackageHandler;
import com.github.dadiyang.nettycs.server.handler.FrameDecoder;
import com.github.dadiyang.nettycs.server.handler.ServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

/**
 * Netty 服务器启动器
 *
 * @author dadiyang
 */
@Slf4j
@Component
@Profile("!test")
@Setter
@ConfigurationProperties(prefix = "nettycs.server")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class NettyServerRunner implements CommandLineRunner {
    private final ServerHandler serverHandler;
    private final EchoPackageHandler echoPackageHandler;
    private int port;

    @Override
    public void run(String... args) throws Exception {
        log.info("服务器启动，监听端口：" + port);
        ServerBootstrap bootstrap = new ServerBootstrap();
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            bootstrap.group(group)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) {
                            log.info("客户端接入: " + socketChannel.remoteAddress());
                            socketChannel.pipeline().addLast("channelIdleHandler", new ChannelIdleHandler())
                                    .addLast("frameDecoder", new FrameDecoder())
                                    .addLast("packetEncoder", new PacketEncoder())
                                    .addLast("packetDecoder", new PacketDecoder())
                                    .addLast("serverHandler", serverHandler)
                                    .addLast("echoPacketHandler", echoPackageHandler);
                        }
                    });
            ChannelFuture future = bootstrap.bind().sync();
            future.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

}
