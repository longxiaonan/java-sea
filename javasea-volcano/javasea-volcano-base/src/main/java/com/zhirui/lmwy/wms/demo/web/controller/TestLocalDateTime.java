package com.zhirui.lmwy.wms.demo.web.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @ClassName TestLocalDateTime
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2018/11/2 0002 9:48
 */
public class TestLocalDateTime {
    public static void main(String[] args) {
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println("系统当前日期+时间:" + localDateTime);//系统当前日期+时间:2018-11-02T09:52:13.644
        LocalDate localDate = LocalDate.now();
        System.out.println("系统当期日期：" + localDate);//系统当期日期：2018-11-02
        LocalTime localTime = LocalTime.now();
        System.out.println("系统当前时间：" + localTime);//系统当前时间：09:52:13.645
        System.out.println("系统当前时间,去除毫秒：" + localTime.withNano(0));//系统当前时间,去除毫秒：09:52:13

        //指定年、月、日、时、分、秒：2016-11-23T09:07:59
        System.out.println("指定年、月、日、时、分、秒：" + LocalDateTime.of(2016, 11, 23, 9, 07, 59));//返回一个指定年、月、日、时、分、秒的时间，不可变
        //指定年数的时间：2018-01-01T09:52:13.644
        System.out.println("指定年数的时间：" + localDateTime.withDayOfYear(1));//返回一个指定“一年中的天数,the day-of-year  ”的时间，不可变
        //指定天数的时间：2018-11-01T09:52:13.644
        System.out.println("指定天数的时间：" + localDateTime.withDayOfMonth(1));//返回一个指定“一月中的天数,day-of-month ”的时间，不可变
        //指定小时数的时间：01:52:13.645
        System.out.println("指定小时数的时间：" + localTime.withHour(1));//返回一个指定“一天中的小时数,the hour-of-day ”的时间，不可变
        //指定分钟数的时间：09:01:13.645
        System.out.println("指定分钟数的时间：" + localTime.withMinute(1));//返回一个指定“一小时的分钟数,the minute-of-hour ”的时间，不可变
        //指定秒数的时间：09:52:01.645
        System.out.println("指定秒数的时间：" + localTime.withSecond(1));//返回一个指定“一分钟的秒数,the second-of-minute ”的时间，不可变
        //the hour-of-day, from 0 to 23：9
        System.out.println("the hour-of-day, from 0 to 23：" + localTime.getHour());//取得一天中的小时数, 当前在一天中的小时数
        //minute-of-hour, from 0 to 59:52
        System.out.println("minute-of-hour, from 0 to 59:" + localTime.getMinute());//取得一小时中的分钟数, 当前在当前小时中的分钟数
        //the second-of-minute, from 0 to 59:13
        System.out.println("the second-of-minute, from 0 to 59:" + localTime.getSecond());//取的一分钟的秒数
        //the nano-of-second, from 0 to 999,999,999:645000000
        System.out.println("the nano-of-second, from 0 to 999,999,999:" + localTime.getNano());//取得一秒钟的纳秒数

        /** 日期运算 */
        System.out.println("系统当前时间减去1年：" + (localDate.minusYears(1)));//系统当前时间减去1年：2017-11-02
        //看看闰年和平年的区别
        LocalDateTime localDateTime1 = LocalDateTime.of(2016, 02, 29, 00, 00, 00);
        System.out.println("闰年减去一年：" + localDateTime1.minusYears(1));//减去一年后是平年，注意结果, 闰年减去一年：2015-02-28T00:00
        System.out.println("系统当前时间减去1天：" + (localDate.minusDays(1)));//系统当前时间减去1天：2018-11-01
        System.out.println("系统当前时间减去23天：" + (localDate.minusDays(23)));//系统当前时间减去23天：2018-10-10
        System.out.println("系统当前时间加上1年：" + (localDate.plusYears(1)));//系统当前时间加上1年：2019-11-02
        System.out.println("系统当前时间加上1天：" + (localDate.plusDays(1)));//系统当前时间加上1天：2018-11-03
        System.out.println("系统当前时间加上1分钟：" + (localDateTime.plusMinutes(1)));//系统当前时间加上1分钟：2018-11-02T09:53:13.644
        System.out.println("系统当前时间加上1纳秒：" + (localDateTime.plusNanos(1)));//系统当前时间加上1纳秒：2018-11-02T09:52:13.644000001
        System.out.println("系统当前时间加上1周：" + (localDateTime.plusWeeks(1)));//系统当前时间加上1周：2018-11-09T09:52:13.644
        System.out.println("localDateTime 转 localDate:" + localDateTime.toLocalDate());//localDateTime 转 localDate:2018-11-02
        System.out.println("localDateTime 转 localTime:" + localDateTime.toLocalTime());//localDateTime 转 localTime:09:52:13.644
        //格式器
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss");
        System.out.println(LocalDateTime.parse("20111123 00:00:00", dateTimeFormatter));//2011-11-23T00:00
        System.out.println(localDateTime.format(dateTimeFormatter));//20181102 10:02:05
        System.out.println(LocalDateTime.MIN);//支持的最小时间, -999999999-01-01T00:00
        System.out.println(LocalDateTime.MAX);//支持的最大时间, +999999999-12-31T23:59:59.999999999
    }
}
