package com.iee.concurrent;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @ClassName: CompletableFutureTest
 * @Description:
 * @author 龙小南
 * @email longxiaonan@163.com
 * @date 2017年12月2日 下午4:23:09
 * @version 1.0
 */
public class CompletableFutureTest {
	public static void main(String[] args) {
		// runAsync无返回值
//		 testRunAsync();

		// supplyAsync有返回值
		 testSupplyAsync();

//		 testComplete();

//		testApplyToEither();

	}

	private static void testApplyToEither() {
		Random random = new Random();
		CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
			try {
				Thread.sleep(random.nextInt(3000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return "from future1";
		});
		CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
			try {
				Thread.sleep(random.nextInt(1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return "from future2";
		});

		//applyToEither方法, 上面的future1和future2谁先完成, 作为参数值在applyToEither()方法中进行计算后返回
		CompletableFuture<String> future = future1.applyToEither(future2, str -> {
			try {
				System.out.println(future2.get()+">>>>");//from future2>>>>
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "The future is " + str;
					});
		try {
			System.out.println(future.get());//The future is from future1
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	private static void testComplete() {
		CompletableFuture<String> future = CompletableFuture.supplyAsync(() ->
				"Hello");
		future.complete("Woｄｄｄrld");// 如果supplyAsync没执行完, 就直接输出"Woｄｄｄrld",
									// 来立即执行完成
		try {
			System.out.println(future.get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	private static void testSupplyAsync() {
		CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> "Hello");
		try {
			Thread.sleep(5000);
			System.out.println(future2.get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		System.out.println("CompletableFuture");

		CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "Hello");
		future.complete("World");
		try {
			System.out.println(future.get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	private static void testRunAsync() {
		CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
			try {
				Thread.sleep(5000);
				System.out.println("Hello");
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		try {
			future.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		System.out.println("CompletableFuture");
	}
}
