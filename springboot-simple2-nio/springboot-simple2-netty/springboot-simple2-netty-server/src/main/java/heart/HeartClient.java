package heart;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

public class HeartClient {
    private final String host;  
    private final int port;  
  
    
    public HeartClient(String host, int port) {
        this.host = host;  
        this.port = port;  
    }  
  
    public void start() throws Exception {  
        EventLoopGroup group = new NioEventLoopGroup();  
        try {  
            Bootstrap b = new Bootstrap();
            b.group(group);  
            b.channel(NioSocketChannel.class);  
            b.remoteAddress(new InetSocketAddress(host, port));
            b.handler(new ChannelInitializer<SocketChannel>() {
  
                public void initChannel(SocketChannel ch) throws Exception {
                	//需要调用connect()方法来连接服务器端，但我们也可以通过调用bind()方法返回的ChannelFuture中获取Channel去connect服务器端。
                    ch.pipeline().addLast(new IdleStateHandler(0, 10, 0, TimeUnit.SECONDS));
                    ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
//                    ch.pipeline().addLast(new StringEncoder());
//                    ch.pipeline().addLast(new StringDecoder());//channelRead的msg自动转换成了String类型
                    ch.pipeline().addLast(new HeartClientHeartBeat());
                }  
            });
            b.option(ChannelOption.SO_BACKLOG, 2048);//serverSocketchannel的设置，链接缓冲池的大小
            b.option(ChannelOption.SO_KEEPALIVE,true);
            //发起异步连接操作
            ChannelFuture f = b.connect().sync();  
            f.addListener(new ChannelFutureListener() {  
                public void operationComplete(ChannelFuture future) throws Exception {
                    if(future.isSuccess()){  
                        System.out.println("client connected");  
                    }else{  
                        System.out.println("server attemp failed");  
                        future.cause().printStackTrace();  
                    }  
                }  
            });  
            //等待客户端链路关闭
            f.channel().closeFuture().sync();  
        } finally {  
            group.shutdownGracefully().sync();  
        }  
    }  
  
    public static void main(String[] args) throws Exception {
        new HeartClient("127.0.0.1", 11111).start();
    }
}  