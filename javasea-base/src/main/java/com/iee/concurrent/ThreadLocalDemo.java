package com.iee.concurrent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName ThreadLocalDemo
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2019/11/4 0004 17:31
 */
public class ThreadLocalDemo {
    private static final ThreadLocal<SimpleDateFormat> local = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

    public static String format(Date date) {
        return local.get().format(date);
    }

    public static Date parse(String dateStr) throws ParseException {
        return local.get().parse(dateStr);
    }

}
