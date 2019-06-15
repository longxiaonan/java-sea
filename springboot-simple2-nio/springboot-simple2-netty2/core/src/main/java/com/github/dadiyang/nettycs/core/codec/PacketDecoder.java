package com.github.dadiyang.nettycs.core.codec;

import com.github.dadiyang.nettycs.core.packet.AbstractPacket;
import com.github.dadiyang.nettycs.core.packet.PacketType;
import com.github.dadiyang.nettycs.core.serialize.Serializer;
import com.github.dadiyang.nettycs.core.serialize.SerializerFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static com.github.dadiyang.nettycs.core.codec.PacketEncoder.MAGIC_NUMBER;


/**
 * 反序列化器，将二进制流转化为我们的消息体
 *
 * @author dadiyang
 */
@Slf4j
@RequiredArgsConstructor
public class PacketDecoder extends ByteToMessageDecoder {
    private static final int HEAD_LENGTH = 11;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < HEAD_LENGTH) {
            return;
        }
        in.markReaderIndex();
        int magic = in.readInt();
        // 魔法数校验
        assert magic == MAGIC_NUMBER;
        try {
            // 版本号，暂时还用不上，如果消息体有需要不兼容地升级的话，可以用
            byte version = in.readByte();
            byte alg = in.readByte();
            byte cmd = in.readByte();
            int length = in.readInt();
            if (in.readableBytes() < length) {
                // 当前读取的长度太小则返回等待下次
                in.resetReaderIndex();
                return;
            }
            byte[] bytes = new byte[length];
            in.readBytes(bytes);
            // 选择合适的反序列化器
            Serializer serializer = SerializerFactory.getSerializer(alg);
            PacketType type = PacketType.getTypeByCommand(cmd);
            // 反序列化
            AbstractPacket packet = serializer.deserialize(bytes, type.getClazz());
            out.add(packet);
        } catch (Exception e) {
            in.resetReaderIndex();
            log.error("decode msg error", e);
        }
    }
}
