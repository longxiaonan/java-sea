package com.iee.concurrent;

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: CompletableFutureTest
 * @Description:
 * @author 龙小南
 * @email longxiaonan@163.com
 * @date 2017年12月2日 下午4:23:09
 * @version 1.0
 */
public class CompletableFutureTest {

	//runAsync无返回值
	@Test
	public void testApplyToEither() {
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

	@Test
	public void testComplete() {
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

	@Test
	public void testSupplyAsync() {
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

	public  void testRunAsync() {
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

	@Test
	public void test10() throws Exception {
		String result = CompletableFuture.supplyAsync(() -> {
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (1 == 1) {
				throw new RuntimeException("an RuntimeException");
			}
			return "s1";
		}).whenComplete((s, t) -> {
			System.out.println("whenComplete s:"+s);
			System.out.println("whenComplete exception:"+t.getMessage());
		}).exceptionally(e -> {
			System.out.println("exceptionally exception:"+e.getMessage());
			return "hello ijiangtao";
		}).join();

		System.out.println(result);
	}
}
