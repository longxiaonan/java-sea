package com.iee.guava;

import com.google.common.base.Splitter;

/**
 * 拆分器返回的是Iterable<String>。
 * 如果想直接返回List，只要使用Lists.newArrayList(splitter.split(string))或类似方法。
 * 重点：splitter实例总是不可变的。
 * 用来定义splitter目标语义的配置方法总会返回一个新的splitter实例。这使得splitter实例都是线程安全的，你可以将其定义为static
 * final常量。（也就是说，可以把split函数之前的代码定义成一个全局静态变量，到处使用）。
 * @author longxn
 *
 */
public class SplitterDemo {
	public static void main(String[] args) {
		/*
		 * on():指定分隔符来分割字符串 limit():当分割的子字符串达到了limit个时则停止分割, 还可以通过正则切割onPattern("[.|,]")
		 * fixedLength():根据长度来拆分字符串 trimResults():去掉子串中的空格
		 * omitEmptyStrings():去掉空的子串
		 * withKeyValueSeparator():要分割的字符串中key和value间的分隔符,
		 * 分割后的子串中key和value间的分隔符默认是=
		 */
		System.out.println(Splitter.on(",").limit(3).trimResults().split(" a,  b,  c,  d"));//[a, b, c,  d],一共3个元素"c,  d"是一个元素
		System.out.println(Splitter.fixedLength(3).split("1 2 3"));// [1 2, 3],一共两个元素
		System.out.println(Splitter.on(" ").omitEmptyStrings().splitToList("1  2 3"));//[1, 2, 3]
		System.out.println(Splitter.on(",").omitEmptyStrings().split("1,,,,2,,,3"));// [1, 2, 3]
		System.out.println(Splitter.on(" ").trimResults().split("1 2 3")); // [1, 2, 3]
		System.out.println(Splitter.on(";").withKeyValueSeparator(":").split("a:1;b:2;c:3"));// {a=1, b=2, c=3}
	}
}
