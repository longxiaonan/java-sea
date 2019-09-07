package com.iee.encodeDecode;

import io.jsonwebtoken.impl.Base64UrlCodec;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Base64;

/**
 * @ClassName Base64Demo
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2019/7/11 0011 12:36
 */
public class Base64Demo {
    /** 输出：
     Base64.getEncoder(): 5oiR5piv5Lit5Zu95Lq677yMKy0q77yBQCPvv6Ul4oCm4oCmPSbjgIFcfHwvLz48Lg==
     Base64.getDecoder(): 我是中国人，+-*！@#￥%……=&、\||//><.
     apache.commons 下 Base64.getEncoder(): 5oiR5piv5Lit5Zu95Lq677yMKy0q77yBQCPvv6Ul4oCm4oCmPSbjgIFcfHwvLz48Lg==
     apache.commons 下 Base64.getDecoder(): 我是中国人，+-*！@#￥%……=&、\||//><.
     URLEncoder.encode(): %E6%88%91%E6%98%AF%E4%B8%AD%E5%9B%BD%E4%BA%BA%EF%BC%8C%2B-*%EF%BC%81%40%23%EF%BF%A5%25%E2%80%A6%E2%80%A6%3D%26%E3%80%81%5C%7C%7C%2F%2F%3E%3C.
     URLDecode.decode(): 我是中国人，+-*！@#￥%……=&、\||//><.
     BASE64URL.encode(): 5oiR5piv5Lit5Zu95Lq677yMKy0q77yBQCPvv6Ul4oCm4oCmPSbjgIFcfHwvLz48Lg
     BASE64URL.decode(): 我是中国人，+-*！@#￥%……=&、\||//><.
     **/
    public static void main(String[] args) {
        //基本的basr64
        String str = "我是中国人，+-*！@#￥%……=&、\\||//><.";
        String s = Base64.getEncoder().encodeToString(str.getBytes());
        System.out.println("Base64.getEncoder(): "+s);
        String s1 = new String(Base64.getDecoder().decode(s));
        System.out.println("Base64.getDecoder(): "+s1);

        //apache common包下的基本的basr64
        String s2 = new org.apache.commons.codec.binary.Base64().encodeToString(str.getBytes());
        System.out.println("apache.commons 下 Base64.getEncoder(): "+s2);
        String s3 = new String(new org.apache.commons.codec.binary.Base64().decode(s2));
        System.out.println("apache.commons 下 Base64.getDecoder(): "+ s3);

        //web开发中参数传递默认的url编码解码方式
        String encode = URLEncoder.encode(str);
        System.out.println("URLEncoder.encode(): "+ encode);
        String decode = URLDecoder.decode(encode);
        System.out.println("URLDecode.decode(): "+ decode);

        //jwt的编码解码方式，在base64基础上，对url中特殊的字符进行处理。
        String encode1 = Base64UrlCodec.BASE64URL.encode(str);
        System.out.println("BASE64URL.encode(): "+ encode1);
        String s4 = Base64UrlCodec.BASE64URL.decodeToString(encode1);
        System.out.println("BASE64URL.decode(): "+ s4);
    }
}
