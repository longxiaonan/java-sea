package com.javasea.jintian.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;

import java.nio.charset.Charset;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 接收数据包
 * @author 陈导
 */
public class MyClientHandler extends SimpleChannelInboundHandler<byte[]> {
//public class MyClientHandler extends SimpleChannelInboundHandler<Object> {
//	Logger log = LoggerFactory.getLogger(TCPDataReceiveHandler.class);

	ExecutorService executorService = Executors.newFixedThreadPool(6); //创建含6条线程的线程池

	/**
	 * 客户端与服务端创建连接的时候调用
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		//存储每一个客户端接入进来时的Channel对象
//		NettyGroup.group.add(ctx.channel());
//		NettyGroup.getInstance().getChannelMap().put(ctx.channel().remoteAddress().toString(),ctx.channel());

//		log.info("客户端与服务端连接开启...");
	}


	@Override
	protected void channelRead0(ChannelHandlerContext ctx, byte[] msg) throws Exception {

//		final byte[] data = (byte[])msg;
//        System.err.println(new String(data,Charset.forName("utf-8")));

//		byte[] data = new byte[msg.readableBytes()];
//        msg.writeBytes(data);
//		byte[] bytes = new byte[msg.readableBytes()];
//		msg.readBytes(bytes);

//		String message = new String(data,Charset.forName("utf-8"));

//		System.err.println("服务端收到消息：" + message);

		String hexStr = bytesToHexString(msg);
		System.out.println("接收到数据包: " + hexStr);
	}


	/**
	 * 读写空闲触发的方法（清除网断情况的channel）
	 * @param ctx
	 * @param evt
	 * @throws Exception
	 */
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if(evt instanceof IdleStateEvent){
			IdleStateEvent event = (IdleStateEvent)evt;

			String eventType = null;

			switch (event.state()){
				case READER_IDLE:
					eventType = "读空闲";
					break;
				case WRITER_IDLE:
					eventType = "写空闲";
					break;
				case ALL_IDLE:
					eventType = "读写空闲";
					break;
			}

			/*log.error(ctx.channel().remoteAddress() + "超时事件：" + eventType + ",将链接断开");

			NettyGroup.group.remove(ctx.channel());
			//将channelMap的链接移除
			String devId = NettyGroup.channelIdMap.remove(ctx.channel().id().toString());
			NettyGroup.channelMap.remove(devId);*/
			ctx.channel().close();
		}
	}

	/**
	 *  客户端与服务端断开连接的时候调用
	 */
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		/*NettyGroup.group.remove(ctx.channel());
		//将channelMap的链接移除
		String devId = NettyGroup.channelIdMap.remove(ctx.channel().id().toString());
		NettyGroup.channelMap.remove(devId);
		log.info("客户端与服务端连接关闭...");*/
	}

	/**
	 * 服务端接收客户端发送过来的数据结束之后调用
	 */
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		//将消息发送队列中的消息写入到SocketChannel中发送给对方
		ctx.flush();
	}

	/**
	 * 工程出现异常的时候调用
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//		cause.printStackTrace();
//		log.info("---------------客户端断开连接---------------");
		//连接关闭ChannelGroup会自动将连接remove
		ctx.close();//关闭连接
	}


	/**
	 * 字节数组转成16进制表示格式的字符串,中间以空格隔开
	 *
	 * @param byteArray
	 *            需要转换的字节数组
	 * @return 16进制表示格式的字符串
	 **/
	public static String bytesToHexString(byte[] byteArray) {
		if (byteArray == null || byteArray.length < 1) {
			throw new IllegalArgumentException("this byteArray must not be null or empty");
		}

		final StringBuilder hexString = new StringBuilder();
		for (int i = 0; i < byteArray.length; i++) {
			if ((byteArray[i] & 0xff) < 0x10) {//0~F前面不零
				hexString.append("0");
			}
			hexString.append(Integer.toHexString(0xFF & byteArray[i])).append(" ");
		}
		return hexString.toString().toUpperCase();
	}


}
