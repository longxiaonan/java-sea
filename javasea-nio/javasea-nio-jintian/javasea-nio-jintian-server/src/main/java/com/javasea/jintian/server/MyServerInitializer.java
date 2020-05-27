package com.javasea.jintian.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * 初始化连接时候的各个组件
 * @author 陈导
 *
 */
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {
	/** 帧结构为： |head(1B)|length(1B)|body(42B)| */
	/** 最大长度 */
	private static final int MAX_FRAME_LENGTH = 1024 * 1024;
	/** 长度字段所占的字节数1B, 长度为44 */
	private static final int LENGTH_FIELD_LENGTH = 1;
	/** 长度偏移，说明head 占1B，length在head后面, head为36 */
	private static final int LENGTH_FIELD_OFFSET = 1;
	/** 数据包长度(44) - lengthFieldOffset(1) - lengthFieldLength(1) - 长度域的值(44) */
	private static final int LENGTH_ADJUSTMENT = -2;
	/** 解码过程中，没有丢弃任何数据 */
	private static final int INITIAL_BYTES_TO_STRIP = 0;

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {

		ChannelPipeline pipeline = ch.pipeline();
		pipeline.addLast(new MyLengthFieldBaseFrameDecoder(MAX_FRAME_LENGTH,LENGTH_FIELD_OFFSET,LENGTH_FIELD_LENGTH,LENGTH_ADJUSTMENT,INITIAL_BYTES_TO_STRIP,false));

//		//接收二进制数据
//		pipeline.addLast("bytesDecoder", new ByteArrayDecoder());
////		 Encoder
//		pipeline.addLast("bytesEncoder", new ByteArrayEncoder());


		//空闲状态检测是处理器，在一定时间范围之内没有读，没有写，或者没有读和写，就会触发idleStateEvent事件
		//在60秒之内没有写，60秒之内没有读，60秒之内没有读和写，TimeUnit.SECONDS（不加这个默认单位是秒）
		//客户端一直往控制台里输入数据，服务端不会写入任何数据，所以会出现写空闲
		//通讯平台不主动断开连接，但对超过240 s未接收到数据的socket链路进行资源回收。
		pipeline.addLast(new IdleStateHandler(200,200,200, TimeUnit.SECONDS));
//		pipeline.addLast(new IdleStateHandler(1000,1000,1000, TimeUnit.SECONDS));
//		pipeline.addLast(new IdleStateHandler(600,600,600, TimeUnit.SECONDS));

//		pipeline.addLast(new LengthFieldBasedFrameDecoder(1024,1,1,0,0));

//		pipeline.addLast(new LengthFieldBasedFrameDecoder(1024,0,4,0,4));
//		pipeline.addLast(new MyLengthFieldBaseFrameDecoder(1024,0,4,0,4,false));
		pipeline.addLast(new MyClientHandler());

	}

}
