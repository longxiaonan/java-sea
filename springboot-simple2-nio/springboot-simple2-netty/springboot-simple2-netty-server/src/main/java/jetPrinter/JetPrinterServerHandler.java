package jetPrinter;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import utils.NumericUtils;
import utils.SenderUtil;

import java.io.UnsupportedEncodingException;

@Sharable // 注解@Sharable可以让它在channels间共享
public class JetPrinterServerHandler extends SimpleChannelInboundHandler<ByteBuf> {
	/**
	 * 当有收到数据的时候执行
	 * 
	 * @param ctx
	 * @param msg
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		System.out.println("server received data :[" + msg +"]");
		ByteBuf buf = (ByteBuf) msg;
		//获取接收到的字节数组
		byte[] receiveByte = null;
		if (buf.hasArray()) {
			receiveByte = buf.array();
		}else{
			receiveByte = new byte[buf.readableBytes()];
			buf.readBytes(receiveByte);
		}
		
		System.out.println("收到的数据为：");
		for (byte b : receiveByte) {
			System.out.print(b + " ");
		}
		System.out.println();
		//转换成String后打印
		try {
			System.out.println("The server receive data : [" + new String(receiveByte, "UTF-8") + "]");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
//		ByteBuf resp = Unpooled.buffer();
//		resp.writeBytes(receiveByte);
		ByteBuf resp = Unpooled.copiedBuffer(receiveByte);//效果和上面两条相同, 将字节数组写入ByteBuf
		System.out.println("写回的数据为：");
		for (byte b : receiveByte) {
			System.out.print(b + " ");
		}
		ctx.writeAndFlush(resp);
	}

	@Override
	protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
		System.out.println("111111111111《《《channelRead0》》》11111111111");
	}

	/**
	 * 通道激活
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
		System.out.println("channel is actived!");
	}

	/**
	 * 当channel完成的时候执行
	 * 
	 * @param ctx
	 */
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {
		/*ctx.writeAndFlush(Unpooled.EMPTY_BUFFER) // flush掉所有写回的数据
				.addListener(ChannelFutureListener.CLOSE); // 当flush完成后关闭channel
		 */	
		ctx.flush();
		}

	/**
	 * 当异常出现的时候进行处理
	 * 
	 * @param ctx
	 * @param cause
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();// 捕捉异常信息
		System.out.println(">>>>>>"+cause.getMessage());
		ctx.close();// 出现异常时关闭channel
	}
}