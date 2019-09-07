package com.zhirui.lmwy.wms.demo.lang;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Random;

/**
 * @ClassName RandomDemo
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2018/11/20 0020 11:42
 */
public class RandomDemo {
    public static void main(String[] args) {
        Random rand = new Random();
        // **生产0(包含)到999(包含)的随机数**
            int shu2 = rand.nextInt(1000);
            System.out.println(shu2);

        //生成0-1之间的double数
        double random = Math.random();  //0.5878472051517342
        System.out.println(random);

        //生成三位随机数，不满三位前面补0
            String random1 = RandomStringUtils.randomNumeric(3);
            System.out.println(random1);

        ////生成四位随机数，不满四位前面补0
            String random2 = RandomStringUtils.random(4, "0123456789");
            System.out.println(random2);

        //字符串截取
        String a = "abc";
//        String substring = a.substring(0, 6);//不推荐，因为可能会出现越界
        String left = StringUtils.left(a, 10);

        String s = StringUtils.leftPad(a, 10, "t");

        System.out.println(String.format("%04d",12345));
    }
}
