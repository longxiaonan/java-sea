package com.iee.concurrent;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @ClassName: thread
 * @Description:
 * @author 龙小南
 * @email longxiaonan@163.com
 * @date 2017年12月2日 下午4:09:27
 * @version 1.0
 */
public class FutureTest{
	public static void main(String[] args) {
		ExecutorService executor = Executors.newCachedThreadPool();
		//1 线程执行的submit方法, 该方法返回值类型是Future
		Future<String> future = executor.submit(() -> { // Lambda 是一个 callable，
														// 提交后便立即执行，这里返回的是
														// FutureTask 实例
			System.out.println("running task");
			Thread.sleep(2000);
			return "return task";
		});

		//2 线程执行的execute方法, 该方法返回值类型是void
		/*ExecutorService  executor2 = Executors.newCachedThreadPool();
		executor2.execute(() -> {

		});*/

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		//前面的callable在其他线程中运行着, 可以做些其他的事情
		System.out.println("do something else");

		try {
			//等待future的执行结果, 执行完毕后打印出来
			System.out.println(future.get());
			System.out.println("继续执行");
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		} finally {
			executor.shutdown();
		}
	}
}
