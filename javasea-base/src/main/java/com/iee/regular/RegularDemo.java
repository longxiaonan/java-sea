package com.iee.regular;

import org.junit.Test;

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
//        demo3();
//        demo2();
//        getNum();
//        String aaaBbbb = camelToUnderline("aaaBbbb");
//        System.out.println(aaaBbbb);
//        underlineToCamel("aaa_ggg");
        System.out.println(getCamelCaseString("aaa_ggg"));

    }

    //下划线转驼峰
    public static String getCamelCaseString(String inputString) {
        StringBuilder sb = new StringBuilder();
        boolean nextUpperCase = false;
        for (int i = 0; i < inputString.length(); i++) {
            char c = inputString.charAt(i);
            switch (c) {
                case '_':
                case '-':
                case '@':
                case '$':
                case '#':
                case ' ':
                case '/':
                case '&':
                    if (sb.length() > 0) {
                        nextUpperCase = true;
                    }
                    break;
                default:
                    if (nextUpperCase) {
                        sb.append(Character.toUpperCase(c));
                        nextUpperCase = false;
                    } else {
                        sb.append(Character.toLowerCase(c));
                    }
                    break;
            }
        }
        return sb.toString();
    }

    //驼峰转下划线
    public static String camelToUnderline(String camelName) {
        return camelName.replaceAll("([A-Z]+)", "_$1").toLowerCase();
    }


    //下划线转驼峰
    public static String underlineToCamel(String underlineName) {
        Matcher matcher = Pattern.compile("(_[a-z]{1})").matcher(underlineName);
        StringBuffer result = new StringBuffer();
        while (matcher.find()) {
            String replacement = matcher.group(1);
            matcher.appendReplacement(result, replacement.replace("_", "").toUpperCase());
        }
            matcher.appendTail(result);
        return result.toString();
    }

    //匹配查找邮箱
    private static void demo3() {
        String s = "我的邮箱是smhjx2006@163.com,我曾经用过hmsykt2015@sina.com.cn,我还用过hmsykt0902@qq.com";
        //String regex = "[FutureDemo-zA-Z0-9_]+@[FutureDemo-zA-Z0-9]+(\\.[FutureDemo-zA-Z]+)+";//较为精确的匹配。
        String regex = "\\w+@\\w+(\\.\\w+)+";
        Pattern p = Pattern.compile(regex);
        Matcher matcher = p.matcher(s);
        while(matcher.find()){
            System.out.println(matcher.group());
        }
    }



    //匹配查找手机号
    private static void demo2() {
        String s = "我的手机是18511866260,我曾用过18987654321,还用过18812345678";
        String regex = "1[3578]\\d{9}";


        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(s);//m.matches()是false

		/*boolean b1 = m.find();//b1是true，必须先find后group才有值。
		System.out.println(b1);
		System.out.println(m.group());//打印第一个手机号码

		boolean b2 = m.find();//b1是true，必须先find后group才有值。没find一次就一个group值。
		System.out.println(b2);
		System.out.println(m.group());*///打印第二个手机号码

        while(m.find())//通过find来不断获取group的值，并且打印出来。
            System.out.println(m.group());
    }

    /*
 * (1)定义一个存放手机号码的数字字符串数组
 * 		{"16210626656","18601066888","13912387666","13156166693","15115888028"}
 * (2)利用正则表达式过滤出符合条件的手机号码，
		规则：第1位是1，第二位可以是数字358其中之一，后面6位任意数字，最后3位为任意相同的数字。
	(3)如：18601066888 13912387666
     思路：
    (1)对数组中的每个元素进行遍历。
    (2)定义一个电话号码的正则，判断每个元素是否符合，输出符合的电话号码。
 */
        public static void getNum(){
            String[] arr = {"16210626656","18601066888","13912387666","13156166693","15115888028"};
            for (int i = 0; i < arr.length; i++) {
                String regex = "1[358]\\d{6}(\\d)\\1{2}";
                if(arr[i].matches(regex)){
                    System.out.println(arr[i]);
                }
            }
        }
}


