package com.iee.lambda;

import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.Callable;

public class CollectionComparator {
	public static void main(String[] args) {
		//lambda
		Comparator<String> comp = (first,second) -> Integer.compare(first.length(), second.length());

//		trycatch();
		arraySort();
	}

	private static void trycatch() {
		//异常处理: try,catch
		Runnable sleeper = () -> {System.out.println("zz"); try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}};

		//异常处理: 通过callable来接收
		Callable callable = () -> {System.out.println("zz"); Thread.sleep(1000);
		return null;};
	}

	private static void arraySort() {
		// 对一个数组排序后输出
		String[] words = { "aa1", "bb1222223552333", "AB5", "aB3333335566" };
		Arrays.sort(words, (first, second) -> Integer.compare((first).length(), second.length()));
		Arrays.stream(words).forEach(System.out::println);

		String[] w1 = { "aa1", "bb1222223552333", "AB5", "aB3333335566" };
		Arrays.sort(w1, String::compareToIgnoreCase);
		Arrays.stream(w1).forEach(System.out::println);
		String[] array = Arrays.stream(w1).toArray(String[]::new);//将流构造成一个String类型的数组

	}
}
