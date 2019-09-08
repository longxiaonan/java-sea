package com.iee.guava;

import com.google.common.base.CharMatcher;

public class CharMatcherDemo {
	public static void main(String[] args) {

		String lettersAndNumbers = "foo989yxbar234";

		String s1 = CharMatcher.digit().retainFrom("abc 123 efg");
		// 通过Or组合多个匹配规则，再进行操作

		boolean result = CharMatcher.inRange('a', 'z').or(CharMatcher.inRange('A', 'Z')).matches('K'); //true

		String s2 = CharMatcher.digit().removeFrom("abc 123 efg");

		String keepAlex = CharMatcher.anyOf("alex").retainFrom("13alexgw4634wfw46");

		System.out.println(keepAlex);
	}
}
