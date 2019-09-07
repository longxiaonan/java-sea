package com.github.dadiyang.nettycs.server.handler;

import com.alibaba.fastjson.JSON;
import com.github.dadiyang.nettycs.core.packet.AbstractPacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 服务端业务处理逻辑
 *
 * @author dadiyang
 * @date 2018/10/5
 */
@Slf4j
@ChannelHandler.Sharable
@Component
public class ServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.debug("msg: " + JSON.toJSONString(msg));
        if (!(msg instanceof AbstractPacket)) {
            // 我们只处理 AbstractPacket 的子类消息
            log.warn("not supported msg: " + JSON.toJSONString(msg));
        } else {
            ctx.fireChannelRead(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("服务器处理消息发生异常", cause);
    }
}
