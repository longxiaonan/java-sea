package heart;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class HeartServer {
    public void start(int port) throws InterruptedException {  
        ServerBootstrap b = new ServerBootstrap();
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {  
            b.group(bossGroup, workerGroup);  
            b.channel(NioServerSocketChannel.class);
//            b.localAddress(new InetSocketAddress(port));
            b.childHandler(new ChannelInitializer<SocketChannel>() {//�����ӵ���ʱ�ᴴ��һ��channel
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new IdleStateHandler(10, 0, 0, TimeUnit.SECONDS));
//                            ch.pipeline().addLast(new StringEncoder());
//                            ch.pipeline().addLast(new StringDecoder());
                            ch.pipeline().addLast("myHandler", new HeartServerHeartBeat());
                        }  
                    });
            b.option(ChannelOption.SO_BACKLOG, 2048);//serverSocketchannel的设置，链接缓冲池的大小.服务端处理客户端连接请求是顺序处理的，所以同一时间只能处理一个客户端连接，多个客户端来的时候，服务端将不能处理的客户端连接请求放在队列中等待处理，backlog参数指定了队列的大小
            b.childOption(ChannelOption.SO_KEEPALIVE, true);//socketchannel的设置,维持链接的活跃，清除死链接.当设置该选项以后，如果在两小时内没有数据的通信时，TCP会自动发送一个活动探测数据报文。
            b.childOption(ChannelOption.TCP_NODELAY, true);//socketchannel的设置,关闭延迟发送

            ChannelFuture f = b.bind(port).sync();

            log.info(HeartServer.class.getName() + " started and listen on " + f.channel().localAddress());

            f.channel().closeFuture().sync();
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            bossGroup.shutdownGracefully().sync();
            workerGroup.shutdownGracefully().sync();
        }  
    }  
    public static void main(String[] args) {  
        try {  
//            new transfer.JetPrinterServer().start(8702);
        	new HeartServer().start(11111);
        } catch (InterruptedException e) {  
            e.printStackTrace();  
        }  
    }  
}  