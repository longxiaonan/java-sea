import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

public class TransferServer {  
    public void start(int port) throws InterruptedException {  
        ServerBootstrap b = new ServerBootstrap();
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {  
            b.group(bossGroup, workerGroup);  
            b.channel(NioServerSocketChannel.class);
            b.localAddress(new InetSocketAddress(port));
            b.childHandler(new ChannelInitializer<SocketChannel>() {//�����ӵ���ʱ�ᴴ��һ��channel
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast("myHandler", new TransferServerHandler());
                        }  
                    });  
            ChannelFuture f = b.bind().sync();
            System.out.println(TransferServer.class.getName() + " started and listen on " + f.channel().localAddress());  
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
//            new TransferServer().start(8702);
        	new TransferServer().start(8712);
        } catch (InterruptedException e) {  
            e.printStackTrace();  
        }  
    }  
}  