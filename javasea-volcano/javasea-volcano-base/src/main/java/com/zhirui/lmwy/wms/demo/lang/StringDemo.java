package com.zhirui.lmwy.wms.demo.lang;

import com.google.common.base.Splitter;
import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.UUID;

/**
 * @ClassName StringDemo
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2019/9/7 0007 12:18
 */
public class StringDemo {
    public static void main(String[] args) {
        //字符串截取
        String a = "abc";
//        String substring = a.substring(0, 6);//不推荐，因为可能会出现越界
        String left = StringUtils.left(a, 10);

        String s = StringUtils.leftPad(a, 10, "t");

        System.out.println(String.format("%04d",12345));
    }

    public static String formatDemo() {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyyMMdd");
        String time = format.format(date);
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if (hashCodeV < 0) {//有可能是负数
            hashCodeV = -hashCodeV;
        }
        // 0 代表前面补充0
        // 4 代表长度为4
        // d 代表参数为正数型
        return time + String.format("%011d", hashCodeV);
    }

    public static void substringDemo(){
        //判断起始
        String a1 = "ELM";
        String aa1 = "ELM1013100N-W";
        boolean b = StringUtils.startsWith(aa1, a1);

        //判断结束
        boolean b1 = StringUtils.endsWith("P", "LLM10060");

        //获取中间值
        String a2 = "ENZ1013[040+040]";
        String a3 = "ENZ1013[040+060]";
        final String s = StringUtils.substringBetween(a3, "[", "]");
        if(StringUtils.isNotBlank(s)) {
            Iterable<String> split1 = Splitter.on("+").split(s);
            Integer sum = 0;
            Iterator<String> iterator = split1.iterator();
            System.out.println(iterator);
            while(iterator.hasNext()){
                String next = iterator.next();
                System.out.println(next);
                sum += Integer.valueOf(next);
            }
            sum *= 10;
        }

        //截取
        String substring = StringUtils.substring(a2, 3, 5);
        System.out.println(substring);
    }
}
