package com.iee.regular;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName qq
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2019/7/20 0020 14:34
 */
public class qq {
    public static void main(String[] args) {

        //驼峰转换：
        String str1 = "createTime";
        String str2 = "createTimeAt";
        String regex = "([A-Z])+";
        System.out.println(str1.replaceAll(regex, "_$1").toLowerCase());//create_time
        System.out.println(str2.replaceAll(regex, "_$1").toLowerCase());//create_time_at

        //通过Matcher的分组功能，可以提取出上面字符串中的手机号
        String data = "哈哈哈，我的手机号码是:12345678901，你会打给我吗";
        Matcher matcher = Pattern.compile(".*(我的手机号码是:([0-9]{11}))").matcher(data);
        while (matcher.find()) {
            System.out.println("G0:" + matcher.group(0));
            System.out.println("G1:" + matcher.group(1));
            System.out.println("G2:" + matcher.group(2));
        }
    }
}
