package com.lognxn.test.controller;

import java.util.Objects;

/**
 * @ClassName test
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2018/11/19 0019 14:50
 */
public class test {

    public static void main(String[] args) {
        Integer a = 400;
        Integer b = 400;
        System.out.println(Objects.equals(a,b));  //推荐，防止空指针
        System.out.println(a.intValue() == b.intValue());
        System.out.println(a.equals(b));
    }

}
