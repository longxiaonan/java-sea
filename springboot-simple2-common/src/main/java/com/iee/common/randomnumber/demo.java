package com.iee.common.randomnumber;

import org.apache.commons.lang3.LocaleUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.FastDateFormat;

import java.util.Date;

/**
 * @ClassName demo
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2018/12/3 0003 20:41
 */
public class demo {
    public static void main(String[] args) {
        String a  = "001001";
        int b = 8;
        int i = 100;
        int i1 = Integer.parseInt(a);
        long l = NumberUtils.toLong(a);
        System.out.println(l);
//        String format = String.format("%0"+b+"d", l);
        String format = String.format("%0"+b+"s", a);
        System.out.println(format);
    }
}
