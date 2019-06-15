package com.github.dadiyang.nettycs.client.handler;

import com.github.dadiyang.nettycs.core.handler.AbstractPacketHandler;
import com.github.dadiyang.nettycs.core.packet.EchoPacket;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * 客户端业务处理逻辑
 *
 * @author dadiyang
 * @date 2018/10/5
 */
@Slf4j
public class EchoClientPacketHandler extends AbstractPacketHandler<EchoPacket> {
    private ScheduledFuture<?> future;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 启动定时任务自动发送消息给服务端
        future = ctx.executor().scheduleAtFixedRate(() -> {
            if (ctx.channel().isActive()) {
                log.info("定时发送下一个请求");
                ctx.writeAndFlush(new EchoPacket("Hello~~: " + new Date()));
            } else if (future != null) {
                log.warn("连接已关闭");
                future.cancel(true);
            }
        }, 3, 3, TimeUnit.SECONDS);

        // 连接建立之后马上发送消息
        ctx.writeAndFlush("Run: " + new Date());
    }

    @Override
    protected void handlePacket(ChannelHandlerContext ctx, EchoPacket msg) throws Exception {
        log.debug("client msg received: " + msg.getText());
    }
}
