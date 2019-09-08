package com.iee.guava;

import com.google.common.base.Strings;

/** 做String为空判断 */
public class StringsDemo {
	public static void main(String[] args) {
		System.out.println(Strings.isNullOrEmpty("")); //true
		System.out.println(Strings.isNullOrEmpty(null)); //true
		System.out.println(Strings.isNullOrEmpty("abc")); //false
		System.out.println(Strings.repeat("ab", 3)); //ababab
	}
}
