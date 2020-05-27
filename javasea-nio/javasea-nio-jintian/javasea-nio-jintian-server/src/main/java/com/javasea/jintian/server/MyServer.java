package com.javasea.jintian.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 注释：
 *
 * @author 陈导
 * @date 2020/5/26 14:49
 */
public class MyServer {

    public static void main(String[] args) throws InterruptedException {
        //配置服务端的NIO线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();

        ServerBootstrap b = new ServerBootstrap();
        //将两个NIO线程组当作入参传递到ServerBootstrap
        b.group(bossGroup, workGroup);
        //设置channel类型，服务端用的是NioServerSocketChannel
        b.channel(NioServerSocketChannel.class);
//                        b.channel(NioDatagramChannel.class);
        b.childHandler(new MyServerInitializer());

        //-------------------------------
        //绑定端口，同步等待成功
//			Channel ch = b.bind(8888).sync().channel();
        //进行阻塞，等待服务端链路关闭之后main函数才退出
//			ch.close().sync();
        //-------------------------------

        //绑定端口，同步等待成功
        ChannelFuture f = b.bind(5501).sync();
        //进行阻塞，等待服务端链路关闭之后main函数才退出
        f.channel().closeFuture().sync();
    }

}
