package com.github.dadiyang.nettycs.server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import lombok.extern.slf4j.Slf4j;

import static com.github.dadiyang.nettycs.core.codec.PacketEncoder.MAGIC_NUMBER;

/**
 * 粘包和拆包处理逻辑
 *
 * @author dadiyang
 * @date 2018/10/8
 */
@Slf4j
public class FrameDecoder extends LengthFieldBasedFrameDecoder {
    private static final int INT_BYTES = 4;

    public FrameDecoder() {
        super(Integer.MAX_VALUE, 7, 4);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        // 验证消息头的魔法数是否正确
        if (in.readableBytes() > INT_BYTES && in.duplicate().readInt() != MAGIC_NUMBER) {
            // 不是我们期望的魔法数说明是非法请求，直接关闭非法连接
            log.warn("非法请求，直接关闭: " + ctx.channel().remoteAddress());
            ctx.channel().close();
            return null;
        }
        return super.decode(ctx, in);
    }
}
