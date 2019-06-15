package com.github.dadiyang.nettycs.core.packet;

import lombok.Data;

/**
 * @author dadiyang
 * @date 2019/1/21
 */
@Data
public class EchoPacket extends AbstractPacket {
    private String text;

    public EchoPacket(String text) {
        this.text = text;
    }

    @Override
    public byte getCommand() {
        return 0;
    }
}
