package com.github.dadiyang.nettycs.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 空闲时长检测处理器，当连接空闲时间过长时尝试关闭连接
 *
 * @author dadiyang
 * @date 2018/10/8
 */
@Slf4j
public class ChannelIdleHandler extends IdleStateHandler {

    public ChannelIdleHandler() {
        super(1, 2, 0, TimeUnit.MINUTES);
    }

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
        log.warn("客户端空闲时间过长，可能已掉线，尝试关闭连接：" + ctx.channel().remoteAddress());
        ctx.channel().close();
    }
}
