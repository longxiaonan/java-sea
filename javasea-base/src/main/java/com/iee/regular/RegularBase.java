package com.iee.regular;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName RegularBase
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2019/7/20 0020 17:02
 */
public class RegularBase {
    public static void main(String[] args) {
        Pattern p = Pattern.compile("a*b");
        Matcher m = p.matcher("aaaaab");
        boolean b = m.matches();
        System.out.println(b);
    }
}
