package com.github.dadiyang.nettycs.core.packet;

/**
 * 数据包类型枚举
 *
 * @author dadiyang
 * @date 2018/10/5
 */
public enum PacketType {
    /**
     * 回音消息
     */
    ECHO((byte) 0, EchoPacket.class);

    /**
     * 每种消息类型对应一个命令
     */
    private byte command;
    /**
     * 每种消息类型对应一个 AbstractPacket 子类
     */
    private Class<? extends AbstractPacket> clazz;

    PacketType(byte command, Class<? extends AbstractPacket> clazz) {
        this.command = command;
        this.clazz = clazz;
    }

    public static PacketType getTypeByCommand(byte command) {
        for (PacketType type : PacketType.values()) {
            if (type.command == command) {
                return type;
            }
        }
        throw new IllegalArgumentException("not supported command: " + command);
    }

    public byte getCommand() {
        return command;
    }

    public Class<? extends AbstractPacket> getClazz() {
        return clazz;
    }

}
