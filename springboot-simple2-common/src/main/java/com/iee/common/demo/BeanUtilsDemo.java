package com.iee.common.demo;

import com.iee.common.demo.pojo.BeanA;
import com.iee.common.demo.pojo.BeanB;
import com.iee.common.utils.thirdsupport.BeanUtilsEx;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @ClassName BeanUtilsDemo
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2018/11/1 0001 15:44
 */
public class BeanUtilsDemo {
    public static void main(String[] args) {
        springTest1();
        apacheTest1();
    }
    //推荐使用spring beans
    public static void springTest1() {
        BeanA ba = new BeanA();
        BeanB bb = new BeanB();
        ba.setName1("ba-name1");
        ba.setName2("ba-name2");
        ba.setBirthday(new Date()); //不可以Date转String
//        ba.setBirthday("2018-11-1");//不能字符串日期转Date类型;
        //不用try catch更加清爽, 第三个参数是ignore的属性, 可以通过扩展忽略点指定属性名的值
        org.springframework.beans.BeanUtils.copyProperties(ba, bb, "name2");
        System.out.println("------------111springTest1-------ba-------");
        System.out.println(ba);
        System.out.println("------------111springTest1-------bb-------");
        System.out.println(bb);
    }

    //不推荐用apache beans，可能会出现一些奇怪的问题
    public static void apacheTest1() {

        BeanA ba = new BeanA();
        BeanB bb = new BeanB();
        ba.setName1("ba-name1");
        ba.setName2("ba-name2");
        ba.setBirthday(new Date());//可以Date转String
//        ba.setBirthday("2018-11-1");//不能字符串日期转Date类型
        //需要try catch掉异常
        try {
            org.apache.commons.beanutils.BeanUtils.copyProperties(bb, ba);
//            BeanUtilsEx.copyProperties(bb, ba);
        } catch (Throwable t) {
        }
        System.out.println("------------222apacheTest1-------ba-------");
        System.out.println(ba);
        System.out.println("-------------222apacheTest1------bb-------");
        System.out.println(bb);
    }
}
