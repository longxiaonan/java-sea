package com.github.dadiyang.nettycs.core.packet;

import lombok.Data;

/**
 * 消息包抽象类
 *
 * @author dadiyang
 * @date 2019/1/21
 */
@Data
public abstract class AbstractPacket {
    private byte version = 1;

    /**
     * 封包的命令
     *
     * @return 封包的命令
     */
    public abstract byte getCommand();
}
