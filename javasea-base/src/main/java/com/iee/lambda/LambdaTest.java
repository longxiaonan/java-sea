package com.iee.lambda;

import com.iee.common.entity.User;
import org.junit.Test;

import java.util.*;

/**
 * @ClassName: LambdaTest
 * @Description:
 * @author 龙小南
 * @email longxiaonan@163.com
 * @date 2017年11月10日 上午12:10:03
 * @version 1.0
 */

public class LambdaTest {

	public static void main(String[] args) {

		//runnable简化的写法
		// runnable();

		//list遍历打印
		// listForEach();

		//set遍历打印
		// setForEach();

		// mapForEach();//???怎么迭代

		//list转换成steam后通过map操作每个值后打印
//		streamAndMap();

		//list转换成steam后通过map操作每个值后, 通过Reduce合并成一个值后返回
//		streamAndReduce();

//		mapToIntSum();

//		filterAndFindAndGet();
	}

	private static void mapToIntSum() {
		int[] aa  = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29};
		aa = new int[]{};
		List<Integer> primes = Arrays.asList(2, 3, 5, 7, 11, 13, 17, 19, 23, 29);
		//求和
		System.out.println(primes.stream().mapToInt((x) -> x).sum());//129
		Arrays.stream(aa).forEach(System.out::println);//0
	}

	private static void filterAndFindAndGet(){
		//User entity
		User user1  =  new User("aa", 11);
		User user2  = new User("bb",22);
		User user3  = new User("cc",33);
		List<User> list = new ArrayList<>();
		list.add(user1);
		list.add(user2);
		list.add(user3);
		String name = "aa";
		User user = list.stream().filter(m -> m.getName() == name).findFirst().orElse(new User());
		System.out.println(user);

		//map
		Map map1 = new HashMap<>();
		map1.put("name", "aa");
		map1.put("age", 11);
		Map map2 = new HashMap<>();
		map2.put("name", "bb");
		map2.put("age", 22);
		Map map3 = new HashMap<>();
		map3.put("name", "aa");
		map3.put("age", 3);
		List<Map> list2 = new ArrayList<>();
		list2.add(map1);
		list2.add(map2);
		list2.add(map3);
		//list2不能为空, 且size()必须大于0, 否则有异常
		Map map = list2.stream().filter(m -> m.get("name") == name).findFirst().get();
		System.out.println(map);
	}

	@Test
	private static void streamAndReduce() {
		// Applying 12% VAT on each purchase
		// Old way:
		List<Integer> costBeforeTax = Arrays.asList(100, 200, 300, 400, 500);
		double total = 0;
		for (Integer cost : costBeforeTax) {
		 double price = cost + .12*cost;
		 total = total + price;

		}
		System.out.println("Total : " + total);

		// New way1:
		Optional<Double> b = costBeforeTax.stream().map((cost) -> cost + .12*cost).reduce((sum, cost) -> sum + cost);
		Double dd = b.get();
		System.out.println(dd);
		// New way2:
		double bill = costBeforeTax.stream().map((cost) -> cost + .12*cost)
		                                    .reduce((sum, cost) -> sum + cost)
		                                    .get();
		System.out.println("Total : " + bill);
	}

	private static void streamAndMap() {
		// 不使用lambda表达式为每个订单加上12%的税
		List<Integer> costBeforeTax = Arrays.asList(100, 200, 300, 400, 500);
		for (Integer cost : costBeforeTax) {
			double price = cost + .12 * cost;
			System.out.println(price);
		}
		// 使用lambda表达式
		// 将 costBeforeTax 列表的每个元素转换成为税后的值。我们将 x -> x*x lambda表达式传到 map()
		// 方法，后者将其应用到流中的每一个元素。然后用 forEach() 将列表元素打印出来。使用流API的收集器类，可以得到所有含税的开销。有
		// toList() 这样的方法将 map 或任何其他操作的结果合并起来。由于收集器在流上做终端操作，因此之后便不能重用流了
		costBeforeTax.stream().map((cost) -> cost + .12 * cost).forEach(System.out::println);
	}

	private static void mapForEach() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("aaa", "111");
		map.put("bbb", "222");
		map.put("ccc", "333");
		map.put("ddd", "444");
		map.forEach((k,v)->System.out.println(k+">>>"+v));
	}

	private static void setForEach() {
		List<String> features = Arrays.asList("Lambdas", "Default Method", "StreamCreate API", "Date and Time API");
		Set<String> set = new HashSet<>();
		set.addAll(features);
		set.forEach(n -> System.out.println(n));
	}

	private static void listForEach() {
		// Java 8之前：
		List<String> features = Arrays.asList("Lambdas", "Default Method", "StreamCreate API", "Date and Time API");
		for (String feature : features) {
			System.out.println(feature);
		}
		// Java 8方式：
//		features.forEach(n -> System.out.println(n));
		features.forEach(System.out::println);
	}

	private static void runnable() {
		// Java 8之前：
		/*
		 * new Thread(new Runnable() {
		 *
		 * @Override public void run() { System.out.println(
		 * "Before Java8, too much code for too little to do"); } }).start();
		 */
		// Java 8方式：
		new Thread(() -> System.out.println("In Java8, Lambda expression rocks !!")).start();
		Runnable runnable2=()->{
			System.out.println("RunningfromLambda");
			};
	}
}
