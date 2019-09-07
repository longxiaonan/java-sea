package com.github.dadiyang.nettycs.core.handler;

import com.github.dadiyang.nettycs.core.packet.AbstractPacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 数据包处理器
 *
 * @author dadiyang
 * @date 2019/1/21
 */
@Slf4j
@ChannelHandler.Sharable
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public abstract class AbstractPacketHandler<T extends AbstractPacket> extends SimpleChannelInboundHandler<T> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, T msg) throws Exception {
        log.debug("handle package: " + msg);
        handlePacket(ctx, msg);
    }

    /**
     * 处理数据包抽象方法，需要子类实现
     *
     * @param ctx 上下文
     * @param msg 消息体
     * @throws Exception 可以抛出任意异常
     */
    protected abstract void handlePacket(ChannelHandlerContext ctx, T msg) throws Exception;

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("处理数据包时发生异常", cause);
    }
}
