package com.iee.guava;

import com.google.common.base.Joiner;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class JoinerDemo {
   public static void main(String[] args) throws IOException {
       /*
         on:制定拼接符号，如：test1-test2-test3 中的 “-“ 符号
         skipNulls()：忽略NULL,返回一个新的Joiner实例
         useForNull(“Hello”)：NULL的地方都用字符串”Hello”来代替
        */
	   /** appendTo到StringBuilder */
       StringBuilder sb=new StringBuilder();
       Joiner.on(",").skipNulls().appendTo(sb,"Hello","guava");

       /** append到输出流 */
       FileWriter writer = new FileWriter("append_text.txt");
       Joiner.on(",").appendTo(writer, " foo ", " bar ");
       writer.close();

       System.out.println(sb);//Hello,guava
       System.out.println(Joiner.on(",").useForNull("none").join(1,null,3));//1,none,3
       System.out.println(Joiner.on(",").skipNulls().join(Arrays.asList(1,2,3,4,null,6)));//1,2,3,4,6
       Map<String,String>map=new HashMap<>();
       map.put("key1","value1");
       map.put("key2","value2");
       map.put("key3","value3");
       System.out.println(Joiner.on(",").withKeyValueSeparator("=").join(map));//key1=value1,key2=value2,key3=value3
   }
}
