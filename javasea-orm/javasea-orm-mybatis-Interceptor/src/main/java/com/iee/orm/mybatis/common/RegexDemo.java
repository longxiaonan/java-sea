package com.iee.orm.mybatis.common;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName RegexDemo
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2019/11/23 0023 16:58
 */
public class RegexDemo {
    /**
     * [abc] a、b 或 c（简单类）中的一个字符。
     * [^abc] 除了 a、b 或 c（否定） 的任何一个字符。
     * [a-zA-Z] a 到 z 或 A 到 Z，两头的字母包括在内（范围）
     * [a-d[m-p]] a 到 d 或 m 到 p：[a-dm-p]（并集）
     * [a-z&&[def]] d、e 或 f（交集）
     * [a-z&&[^bc]] a 到 z，除了 b 和 c：[ad-z]（减去）
     * [a-z&&[^m-p]] a 到 z，而非 m 到 p：[a-lq-z]（减去）
     * [0-9] 0到9的字符都包括
     */
    public static void main(String[] args) {
        //demo1();
        //demo2();
        //demo3();
        //demo4();
        //demo5();
        //demo6();
        demo7();
    }

    private static void demo7() {
        //分组写法
        String regex = "(.*)(where)(.*)";
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher("aaa where aa where);a");
        if (matcher.find()) {
            String whereLast = matcher.group(matcher.groupCount());
            System.out.println(whereLast);
            System.out.println(StringUtils.indexOfAny(")", whereLast));
        }

        System.out.println(StringUtils.contains("sabc)", ")"));

        System.out.println(StringUtils.countMatches("sa)bc)bc", "bc)"));
        System.out.println(StringUtils.countMatches("sa)bc()bc", ")"));
        System.out.println(StringUtils.countMatches("sa(bc()bc", ")"));
    }

    private static void demo6() {
        String regex = "[a-z&&[^bc]]";
        System.out.println("a".matches(regex));
        System.out.println("b".matches(regex));
        System.out.println("1".matches(regex));
    }

    private static void demo5() {
        String regex = "[a-z&&[def]]";                        //取交集
        System.out.println("a".matches(regex));
        System.out.println("d".matches(regex));
    }

    private static void demo4() {
        String regex = "[a-d[m-p]]";
        System.out.println("a".matches(regex));
        System.out.println("m".matches(regex));
        System.out.println("n".matches(regex));
    }

    private static void demo3() {
        String regex = "[a-zA-Z]";
        System.out.println("a".matches(regex));
        System.out.println("A".matches(regex));
        System.out.println("z".matches(regex));
        System.out.println("Z".matches(regex));
        System.out.println("1".matches(regex));
        System.out.println("%".matches(regex));
    }

    private static void demo2() {
        String regex = "[^abc]";
        System.out.println("a".matches(regex));
        System.out.println("b".matches(regex));
        System.out.println("c".matches(regex));
        System.out.println("d".matches(regex));
        System.out.println("1".matches(regex));
        System.out.println("%".matches(regex));
        System.out.println("10".matches(regex));        //10代表1字符和0字符,不是单个字符
    }

    private static void demo1() {
        String regex = "[abc]";                    //[]代表单个字符
        System.out.println("a".matches(regex));
        System.out.println("b".matches(regex));
        System.out.println("c".matches(regex));
        System.out.println("d".matches(regex));
        System.out.println("1".matches(regex));
        System.out.println("%".matches(regex));
    }
}
