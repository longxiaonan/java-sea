package com.github.dadiyang.nettycs.core.serialize;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 序列化器工厂
 *
 * @author dadiyang
 * @date 2019/1/21
 */
public class SerializerFactory {
    private static final JSONSerializer DEFAULT_SERIALIZER = new JSONSerializer();
    private static Map<Byte, Serializer> serializerMap;

    static {
        Map<Byte, Serializer> map = new HashMap<>();
        // 添加所有支持的序列化器
        map.put(DEFAULT_SERIALIZER.getSerializeAlgorithm(), DEFAULT_SERIALIZER);
        // 支持的序列化算法在启动之后不允许动态修改
        serializerMap = Collections.unmodifiableMap(map);
    }

    /**
     * 根据算法编号获取序列化器
     *
     * @param algorithm 算法编号
     * @return 序列化器
     */
    public static Serializer getSerializer(byte algorithm) {
        return serializerMap.getOrDefault(algorithm, DEFAULT_SERIALIZER);
    }
}
