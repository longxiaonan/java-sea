package com.javasea.jintian.server.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.nio.charset.Charset;

public class MyClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private int count;

    /**
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        /*for(int i = 0;i<10;++i){
//            ByteBuf buffer = Unpooled.copiedBuffer("send from client ", Charset.forName("utf-8"));
//            ctx.writeAndFlush(buffer);
            LengthFieldBasedFrameDecoder spliter=new LengthFieldBasedFrameDecoder(1024,0,4,0,4);

            ByteBuf buf = Unpooled.buffer();
            String s = "呵呵,I am " + i;
            byte[] bytes = s.getBytes("UTF-8");
            buf.writeInt(bytes.length);
            buf.writeBytes(bytes);
            ctx.writeAndFlush(buf);
        }*/

        //数据包断包黏包问题
        for(int i = 0;i<10;++i){
            String s = "24 2C 64 02 20 04 10 46 01 37 34 66 62 38 32 62 62 63 35 39 37 34 37 37 38 39 61 34 32 38 30 63 65 61 31 61 61 37 62 66 38 42 C8 23";
            String[] split = s.split(" ");
            byte[] bys=new byte[split.length];
            for (int a=0;a<split.length;a++) {
                short tmp = Short.valueOf(split[a], 16);
                bys[a]=(byte)tmp;
            }
            for(int j=0; j<bys.length; j++){
                System.out.print(bys[j]+",");
            }
            System.out.println();
            ByteBuf buffer = Unpooled.buffer();
            buffer.writeBytes(bys);
            ctx.writeAndFlush(buffer);
        }

    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        byte[] buffer = new byte[msg.readableBytes()];
        msg.readBytes(buffer);

        String message = new String(buffer, Charset.forName("utf-8"));
        System.out.println("客户端接收到的消息内容：" + message);
        System.out.println("客户端接收到的消息数量：" + (++this.count));

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
