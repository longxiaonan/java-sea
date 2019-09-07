package com.github.dadiyang.nettycs.core.serialize;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;

/**
 * JSON序列化实现
 *
 * @author dadiyang
 * @date 2018/10/5
 */
@Component
public class JSONSerializer implements Serializer {
    @Override
    public byte getSerializeAlgorithm() {
        return SerializerAlgorithm.JSON.getAlg();
    }

    @Override
    public byte[] serialize(Object object) {
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        return JSON.parseObject(bytes, clazz);
    }
}
