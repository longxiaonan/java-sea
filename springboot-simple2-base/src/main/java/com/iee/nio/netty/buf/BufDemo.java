package com.iee.nio.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;

/**
 * @ClassName BufDemo
 * @Description 大端，小端的netty版本
 * @Author longxiaonan@163.com
 * @Date 2019/5/24 0024 9:24
 */
public class BufDemo {
    public static void main(String[] args) {
        PooledByteBufAllocator allocator = new PooledByteBufAllocator();
        ByteBuf buf = allocator.buffer(); // 分配一ByteBuf
        String s = "9C33 2D32";
        String[] splits = s.split(" "); // 切分为 "9C33"和"2D32"
        for (String split : splits) { // 第一次 split = "9C33"
            String s1 = split.substring(0, 2); // s1 = "9C"
            String s2 = split.substring(2, 4); // s2 = "33"
            int i1 = Integer.valueOf(s1, 16); // 9C是第一个字节
            buf.writeByte((byte)i1); // 把字节写进去ByteBuf
            int i2 = Integer.valueOf(s2, 16); // 33是第二个字节
            buf.writeByte((byte)i2); // 把字节写进去ByteBuf
        }

//        int rsrp = buf.readUnsignedShortLE(); // 按小端模式读取无符号Short的值
        int rsrp = buf.readUnsignedShort();
        rsrp -= 0x3333; // 减去偏移量
        System.out.println(rsrp); // 105
//        int snr = buf.readUnsignedShortLE(); // 按小端模式读取无符号Short的值
        int snr = buf.readUnsignedShort();
        snr -= 0x3333; // 减去偏移量
        System.out.println(snr); // -262

        buf.release(); // 释放引用
    }
}
