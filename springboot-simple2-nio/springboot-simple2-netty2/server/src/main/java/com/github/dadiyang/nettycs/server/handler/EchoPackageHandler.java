package com.github.dadiyang.nettycs.server.handler;

import com.github.dadiyang.nettycs.core.handler.AbstractPacketHandler;
import com.github.dadiyang.nettycs.core.packet.EchoPacket;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author dadiyang
 * @date 2019/1/21
 */
@Slf4j
@Component
public class EchoPackageHandler extends AbstractPacketHandler<EchoPacket> {

    @Override
    protected void handlePacket(ChannelHandlerContext ctx, EchoPacket msg) throws Exception {
        log.info("收到echoPacket: " + msg.getText());
        // 我们将消息体“回音”给客户端
        Thread.sleep(3000);
        ctx.writeAndFlush(msg);
    }
}
