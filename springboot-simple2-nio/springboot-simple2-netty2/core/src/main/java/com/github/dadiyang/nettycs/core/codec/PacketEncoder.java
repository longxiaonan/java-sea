package com.github.dadiyang.nettycs.core.codec;

import com.github.dadiyang.nettycs.core.packet.AbstractPacket;
import com.github.dadiyang.nettycs.core.serialize.Serializer;
import com.github.dadiyang.nettycs.core.serialize.SerializerAlgorithm;
import com.github.dadiyang.nettycs.core.serialize.SerializerFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 封包Encoder
 *
 * @author dadiyang
 * @date 2018/10/5
 */
@ChannelHandler.Sharable
public class PacketEncoder extends MessageToByteEncoder<AbstractPacket> {
    public static final int MAGIC_NUMBER = 0x98653311;
    /**
     * 默认序列化算法
     */
    private static final SerializerAlgorithm DEFAULT_SERIALIZE_ALGORITHM = SerializerAlgorithm.JSON;
    private SerializerAlgorithm algorithm;

    /**
     * 使用默认的序列化算法
     */
    public PacketEncoder() {
        algorithm = DEFAULT_SERIALIZE_ALGORITHM;
    }

    /**
     * 使用指定的序列化算法进行序列化
     *
     * @param algorithm 算法
     */
    public PacketEncoder(SerializerAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

    /**
     * 协议格式
     * <p>
     * 魔术值(4位) - 版本号(1位) - 序列化算法(1位) - 封包命令(1位) - 数据长度(4位) - 内容(数据长度指定的位数)
     */
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, AbstractPacket packet, ByteBuf byteBuf) {
        // 选择
        Serializer serializer = SerializerFactory.getSerializer(algorithm.getAlg());
        byte[] bytes = serializer.serialize(packet);
        // 前四位存放法魔值，用于校验
        byteBuf.writeInt(MAGIC_NUMBER);
        // 第5位存放封包版本号
        byteBuf.writeByte(packet.getVersion());
        // 第6位存放序列化算法
        byteBuf.writeByte(serializer.getSerializeAlgorithm());
        // 第7位存放封包的命令
        byteBuf.writeByte(packet.getCommand());
        // 第8到11位存放数据内容的长度
        byteBuf.writeInt(bytes.length);
        // 之后存放数据内容
        byteBuf.writeBytes(bytes);
    }
}
