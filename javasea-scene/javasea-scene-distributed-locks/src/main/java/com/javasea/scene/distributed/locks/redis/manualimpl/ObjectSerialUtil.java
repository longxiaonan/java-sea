package com.javasea.scene.distributed.locks.redis.manualimpl;

import lombok.Cleanup;

import java.io.*;

public class ObjectSerialUtil {

    private ObjectSerialUtil() {
//        工具类
    }

    /**
     * 将Object对象序列化为byte[]
     *
     * @param obj 对象
     * @return byte数组
     * @throws Exception
     */
    public static byte[] objectToBytes(Object obj) throws IOException {
        @Cleanup ByteArrayOutputStream bos = new ByteArrayOutputStream();
        @Cleanup ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(obj);
        byte[] bytes = bos.toByteArray();
        bos.close();
        oos.close();
        return bytes;
    }


    /**
     * 将bytes数组还原为对象
     *
     * @param bytes
     * @return
     * @throws Exception
     */
    public static Object bytesToObject(byte[] bytes) {
        try {
            ByteArrayInputStream bin = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bin);
            return ois.readObject();
        } catch (Exception e) {
            throw new RuntimeException("反序列化出错！", e);
        }
    }
}
