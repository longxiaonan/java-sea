package com.iee.guava;

import com.google.common.base.Optional;

import java.util.Date;
import java.util.Set;

public class OptionalTest {
	public static void main(String[] args) {
		optionalTest();

		Date d = new Date(1517997624626L);
		System.out.println(d);
	}

	private static void optionalTest() {
		Integer v1 = null;
		Integer v2 = 10;
		//为空时的处理
		Integer o1 = Optional.fromNullable(v1).orNull();//null
		Integer u1 = java.util.Optional.ofNullable(v1).orElse(null);//null
		System.out.println(o1);
		System.out.println(u1);

		boolean p1 = Optional.fromNullable(v1).isPresent();//false
		boolean p2 = Optional.fromNullable(v2).isPresent();//true

		Integer o11 = Optional.fromNullable(v1).or(0);//0
		Integer o22 = java.util.Optional.ofNullable(v1).orElse(0);//0

		Set<Integer> a1 = Optional.fromNullable(v1).asSet();//[]
		Set<Integer> a2 = Optional.fromNullable(v2).asSet();//[10]

		String s = "abcdefg";
		Integer transform = Optional.fromNullable(s).transform(n -> n.length()).get();//7
		Integer orElse = java.util.Optional.ofNullable(s).map(n -> n.length()).orElse(0);//7
	}
}
