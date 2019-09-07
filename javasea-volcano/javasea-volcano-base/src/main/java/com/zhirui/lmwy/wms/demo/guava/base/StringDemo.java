package com.zhirui.lmwy.wms.demo.guava.base;

import com.google.common.base.Strings;

/**
 * @ClassName StringDemo
 * @Description 在google guava中为字符串操作提供了很大的便利，
 * 有老牌的判断字符串是否为空字符串或者为null，
 * 用指定字符填充字符串，以及拆分合并字符串，字符串匹配的判断等等。
 * @Author longxiaonan@163.com
 * @Date 2019/2/19 0019 10:27
 */
public class StringDemo {
    public static void main(String[] args) {
//        1. 使用com.google.common.base.Strings类的isNullOrEmpty(input)方法判断字符串是否为空
//        Strings.isNullOrEmpty(input) demo
        String input = "";
        boolean isNullOrEmpty = Strings.isNullOrEmpty(input);
        System.out.println("input " + (isNullOrEmpty?"is":"is not") + " null or empty.");

        //2. 获得两个字符串相同的前缀或者后缀
        //Strings.commonPrefix(a,b) demo
        String a = "com.jd.coo.Hello";
        String b = "com.jd.coo.Hi";
        String ourCommonPrefix = Strings.commonPrefix(a,b);
        System.out.println("a,b common prefix is " + ourCommonPrefix);//a,b common prefix is com.jd.coo.H

        //Strings.commonSuffix(a,b) demo
        String c = "com.google.Hello";
        String d = "com.jd.Hello";
        String ourSuffix = Strings.commonSuffix(c,d);
        System.out.println("c,d common suffix is " + ourSuffix);//c,d common suffix is .Hello

        //3. Strings的padStart和padEnd方法来补全字符串
        int minLength = 4;
        String padEndResult = Strings.padEnd("123", minLength, '0');
        System.out.println("padEndResult is " + padEndResult);

        String padStartResult = Strings.padStart("1", 2, '0');
        System.out.println("padStartResult is " + padStartResult);

    }
}
