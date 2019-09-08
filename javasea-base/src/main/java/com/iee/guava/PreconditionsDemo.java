package com.iee.guava;

import com.google.common.base.Preconditions;

import java.util.Optional;

/**
 * 作为调节判断
 *
 * @author longxn
 *
 */

public class PreconditionsDemo {
	public static void main(String[] args) {

		try {
			System.out.println(getValue(4));
		} catch (IndexOutOfBoundsException e) {
			System.out.println(e.getMessage());
		}
		try {
			sum(4, null);
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
		}
		try {
			sqrt(-1);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}

	}

	private static double sqrt(double input) {// IllegalArgumentException
		// 通过Preconditions实现参数合法性判断, 为false, 则抛出异常
		Preconditions.checkArgument(input > 0.0, "Illegal Argument passed: Negative value %s.", input);
		// 通过Optional+filter实现参数合法性判断, 不合法则抛出异常
		Optional.ofNullable(input).filter(i -> i > 0).orElseThrow(() -> new IllegalArgumentException("sdf"));

		return Math.sqrt(input);
	}

	private static int sum(Integer a, Integer b) {// NullPointerException
		a = Preconditions.checkNotNull(a, "Illegal Argument passed: First parameter is Null.");
		b = Preconditions.checkNotNull(b, "Illegal Argument passed: sec parameter is Null.");
		return a + b;
	}

	private static int getValue(int input) {// IndexOutOfBoundsException
		int[] data = { 11, 22, 33, 44, 55 };
		int index = Preconditions.checkElementIndex(input, data.length, "Illegal Argument passed: Invalid index.");
		return data[index];
	}
}
