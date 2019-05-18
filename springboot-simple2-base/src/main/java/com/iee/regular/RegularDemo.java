package com.iee.regular;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import org.apache.commons.lang3.StringUtils;

import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import java.util.Spliterators;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName RegularDemo
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2019/4/28 0028 21:06
 */
public class RegularDemo {
    public static void main(String[] args) {
//        Pattern pattern = Pattern.compile("^([\\d]{1,4}[x][\\d]{1,4}|[\\d]{1,4})(.*)$");
//            Matcher matcher = pattern.matcher(o.getPartsName());
//            if (matcher.matches()) {
//              obj.setName(matcher.group(1) + "x" + o.getLength().intValue() + matcher.group(2));
//            } else {
//              obj.setName(o.getLength().intValue() + o.getPartsName());
//            }

        boolean m1 = Pattern.matches("\\*", "ELM1013100N-W");
        System.out.println(m1);

        boolean m3 = Pattern.matches("^ELM", "ELM1013100N-W");
        System.out.println(m3);

        boolean m2 = Pattern.matches("\\.", "ELM1013100N-W");
        System.out.println(m2);
//        System.out.println(matches);//false
//        boolean m1 = Pattern.matches("\\[", "");

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
