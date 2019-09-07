package com.github.dadiyang.nettycs.core.serialize;

/**
 * 序列化器
 *
 * @author dadiyang
 * @date 2018/10/5
 */
public interface Serializer {

    /**
     * 获取序列化算法
     *
     * @return 序列化算法
     */
    byte getSerializeAlgorithm();

    /**
     * 序列化
     *
     * @param object 被序列化的对象
     * @return 序列化后的二进制数组
     */
    byte[] serialize(Object object);

    /**
     * 反序列化
     *
     * @param bytes 二进制数组
     * @param clazz 目标类
     * @param <T>   目标类型
     * @return 反序列化后的对象
     */
    <T> T deserialize(byte[] bytes, Class<T> clazz);
}
