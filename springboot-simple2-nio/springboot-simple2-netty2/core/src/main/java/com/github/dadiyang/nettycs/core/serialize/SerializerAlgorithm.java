package com.github.dadiyang.nettycs.core.serialize;

/**
 * 枚举所有的序列化算法
 *
 * @author dadiyang
 * @date 2018/10/5
 */
public enum SerializerAlgorithm {
    /**
     * json
     */
    JSON((byte) 0);
    private byte alg;

    SerializerAlgorithm(byte alg) {
        this.alg = alg;
    }

    public byte getAlg() {
        return alg;
    }
}
