package com.javasea.mq.rabbitmq.mgr;

import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.text.NumberFormat;

/**
 * @author 龙小南
 * @version 1.0
 * @ClassName: TypeConversionUtils
 * @Description:
 * @email longxiaonan@163.com
 * @date 2017年11月25日 下午10:56:32
 */
@Slf4j
public class TypeConversionUtils {
    public static void main(String[] args) {
        BigDecimal decimal = new BigDecimal("1.12346");
        System.out.println(decimal);
        BigDecimal setScale = decimal.setScale(4, BigDecimal.ROUND_HALF_DOWN);
        System.out.println(setScale);

        BigDecimal setScale1 = decimal.setScale(4, BigDecimal.ROUND_HALF_UP);
        System.out.println(setScale1);
    }

    /**
     * double类型转BigDecimal
     *
     * @param d         需要转换的double类型数字
     * @param holdCount 保留的小数位数
     * @return
     */
    public static BigDecimal Double2BigDecimal(double d, int holdCount) {
        NumberFormat ddf1 = NumberFormat.getNumberInstance();
        ddf1.setMaximumFractionDigits(holdCount);
        String s = ddf1.format(d);
        s = s.replace(",", "");// s有时候会为111,2423.00这种格式, 需要替换掉逗号
        return BigDecimal.valueOf(Double.valueOf(s));
    }

    /**
     * BigDecimal的四舍五入
     *
     * @param decimal   需要转换的数据
     * @param holdCount 需要保留的位数
     */
    public static BigDecimal BigDeimalSetScale(BigDecimal decimal, int holdCount) {
        BigDecimal setScale = decimal.setScale(holdCount, BigDecimal.ROUND_HALF_UP);
        return setScale;
    }

    /**
     * 对象转byte
     *
     * @param obj
     * @return
     */
    public static byte[] ObjectToByte(Object obj) {
        byte[] bytes = null;
        try (
                ByteArrayOutputStream bo = new ByteArrayOutputStream();
                ObjectOutputStream oo = new ObjectOutputStream(bo)
        ) {
            oo.writeObject(obj);
            bytes = bo.toByteArray();
        } catch (Exception e) {
            System.out.println("translation" + e.getMessage());
            log.error("",e);
        }
        return bytes;
    }

    /**
     * byte转对象
     *
     * @param bytes
     * @return
     */
    public static Object ByteToObject(byte[] bytes) {
        Object obj = null;
        try (
                // bytearray to object
                ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
                ObjectInputStream oi = new ObjectInputStream(bi)
        ) {
            obj = oi.readObject();
        } catch (Exception e) {
            System.out.println("translation" + e.getMessage());
            log.error("",e);
        }
        return obj;
    }

}
