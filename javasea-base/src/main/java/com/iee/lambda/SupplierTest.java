package com.iee.lambda;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

/**
 * @ClassName: Car
 * @Description:
 * @author 龙小南
 * @email longxiaonan@163.com
 * @date 2017年11月29日 下午11:54:08
 * @version 1.0
 */
public class SupplierTest {
	public static SupplierTest create(final Supplier<SupplierTest> supplier) {
		return supplier.get();
	}

	public static void collide(final SupplierTest car) {
		System.out.println("Collided " + car.toString());
	}

	public void follow(final SupplierTest another) {
		System.out.println("Following the " + another.toString());
	}

	public void repair() {
		System.out.println("Repaired " + this.toString());
	}

	public static void main(String[] args) {
		SupplierTest car = SupplierTest.create( SupplierTest::new );
		List< SupplierTest > cars = Arrays.asList( car );
		System.out.println(cars);
	}
}
