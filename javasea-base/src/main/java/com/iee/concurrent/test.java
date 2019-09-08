package com.iee.concurrent;


import java.util.concurrent.*;

public class test {
	static ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		// testCacheThread();
		// testFutureTask();
		// testForkPool();
		// testFixdThread();
		testThreadPoolExecutor();
	}

	private static void testThreadPoolExecutor() {
		// 当执行的线程总数大于maximumPoolSize时会抛出异常!!!
		ThreadPoolExecutor executor3 = new ThreadPoolExecutor(3, 6, 0, TimeUnit.SECONDS,
				new SynchronousQueue<Runnable>());
		// 模拟FixedThreadPool,keepAliveTime需要设置为0,core
		// poolSize=maxPoolSize,allowCoreThreadTimeOut不能设置为true
		ThreadPoolExecutor executor4 = new ThreadPoolExecutor(3, 3, 0, TimeUnit.SECONDS,
				new LinkedBlockingQueue<Runnable>());
		// 当LinkedBlockingQueue改成SynchronousQueue时会抛异常,配置SynchronousQueue时不能将allowCoreThreadTimeOut设置为true
		// 设置allowCoreThreadTimeOut为true可以实现线程回收,maximumPoolSize等于corePoolSize或者大于都行
		// 执行的时候最大的线程数为corePoolSize,而不是maxPoolSize
		ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 3, 20, TimeUnit.SECONDS,
				new LinkedBlockingQueue<Runnable>());
		ThreadPoolExecutor executor1 = (ThreadPoolExecutor) Executors.newCachedThreadPool();
		ThreadPoolExecutor executor2 = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
		// keepAliveTime为0时, 不可以设置为true
		executor.allowCoreThreadTimeOut(true);
		for (int i = 0; i < 1000; i++) {
			final int index = i;
			try {
				Thread.sleep(index * 10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			executor.execute(() -> {
				try {
					Thread.sleep(800);
					System.out.println(Thread.currentThread().getName() + ">>>" + index);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			/** 线程池参数获取 */
			// 获取线程池中活跃的线程数, 一般是0条或者1条, 没参考意义
			int activeCount = executor.getActiveCount();
			// 线程池中的线程数,包括活跃和不活跃的
			int poolSize = executor.getPoolSize();
			// 一直是0,没参考意义
			int corePoolSize = executor.getCorePoolSize();
			// 通过线程池中的线程处理完的任务数量, 可以统计所有线程的执行完成的次数, 不包括正在执行的线程
			long completedTaskCount = executor.getCompletedTaskCount();
			// 统计所有线程的执行的次数, 包括当前的线程, 所以比completedTaskCount + 1
			long taskCount = executor.getTaskCount();
			// 最大线程数量, 为2147483647, 即int类型的最大值范围
			int maximumPoolSize = executor.getMaximumPoolSize();
			// 程序执行时线程池中存在过的 最大线程数量, 有参考意义
			int largestPoolSize = executor.getLargestPoolSize();
			System.err.println("activeCount>>>" + activeCount);// 获取futureTask的返回值
			System.err.println("poolSize>>>" + poolSize);
			System.err.println("corePoolSize>>>" + corePoolSize);
			System.err.println("completedTaskCount>>>" + completedTaskCount);
			System.err.println("taskCount>>>" + taskCount);
			System.err.println("maximumPoolSize>>>" + maximumPoolSize);
			System.err.println("largestPoolSize>>>" + largestPoolSize);
		}

	}

	/**
	 * 固定线程池大小,不会自动回收空闲线程,线程池队列是SynchronousQueue
	 */
	private static void testFixdThread() {
		// new ThreadFactory() {
		// @Override
		// public Thread newThread(Runnable r) {
		// return null;
		// }
		// };
		// Executors.newCachedThreadPool(threadFactory);

		ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(3);
		for (int i = 0; i < 1000; i++) {
			final int index = i;
			try {
				Thread.sleep(index * 100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Future<?> submit = newFixedThreadPool.submit(() -> {
				try {
					Thread.sleep(300);
					System.out.println(Thread.currentThread().getName() + ">>>" + index);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});

			/** 线程池参数获取 */
			// 获取线程池中活跃的线程数, 一般是0条或者1条, 没参考意义
			int activeCount = ((ThreadPoolExecutor) newFixedThreadPool).getActiveCount();
			// 线程池中的线程数,包括活跃和不活跃的
			int poolSize = ((ThreadPoolExecutor) newFixedThreadPool).getPoolSize();
			// 一直是0,没参考意义
			int corePoolSize = ((ThreadPoolExecutor) newFixedThreadPool).getCorePoolSize();
			// 通过线程池中的线程处理完的任务数量, 可以统计所有线程的执行完成的次数, 不包括正在执行的线程
			long completedTaskCount = ((ThreadPoolExecutor) newFixedThreadPool).getCompletedTaskCount();
			// 统计所有线程的执行的次数, 包括当前的线程, 所以比completedTaskCount + 1
			long taskCount = ((ThreadPoolExecutor) newFixedThreadPool).getTaskCount();
			// 最大线程数量, 为2147483647, 即int类型的最大值范围
			int maximumPoolSize = ((ThreadPoolExecutor) newFixedThreadPool).getMaximumPoolSize();
			// 程序执行时线程池中存在过的 最大线程数量, 有参考意义
			int largestPoolSize = ((ThreadPoolExecutor) newFixedThreadPool).getLargestPoolSize();
			System.err.println("activeCount>>>" + activeCount);// 获取futureTask的返回值
			System.err.println("poolSize>>>" + poolSize);
			System.err.println("corePoolSize>>>" + corePoolSize);
			System.err.println("completedTaskCount>>>" + completedTaskCount);
			System.err.println("taskCount>>>" + taskCount);
			System.err.println("maximumPoolSize>>>" + maximumPoolSize);
			System.err.println("largestPoolSize>>>" + largestPoolSize);
		}
	}

	static ForkJoinPool pool = new ForkJoinPool();

	// 固定8个worker, 只有执行完会return,return后才回收线程
	private static void testForkPool() {
		for (int i = 0; i < 1000; i++) {
			final int index = i;
			try {
				Thread.sleep(index * 10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			CompletableFuture.runAsync(() -> {
				try {
					Thread.sleep(300);
					System.out.println(Thread.currentThread().getName() + ">>>" + index);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}, pool);
		}
	}

	/**
	 * cachedThreadPool动态创建线程, 线程池中60s未使用的线程会回收
	 *
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	private static void testFutureTask() throws InterruptedException, ExecutionException {
		for (int i = 0; i < 1000; i++) {
			final int index = i;
			try {
				Thread.sleep(index * 10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			FutureTask<String> futureTask = new FutureTask<String>(() -> {
				Thread.sleep(300);
				System.out.println(Thread.currentThread().getName() + ">>>" + index);
				return "11";// 返回值
			});
			Future<?> submit = cachedThreadPool.submit(futureTask);

			/** 线程池参数获取 */
			// 获取线程池中活跃的线程数, 一般是0条或者1条, 没参考意义
			int activeCount = ((ThreadPoolExecutor) cachedThreadPool).getActiveCount();
			// 线程池中的线程数,包括活跃和不活跃的
			int poolSize = ((ThreadPoolExecutor) cachedThreadPool).getPoolSize();
			// 一直是0,没参考意义
			int corePoolSize = ((ThreadPoolExecutor) cachedThreadPool).getCorePoolSize();
			// 通过线程池中的线程处理完的任务数量, 可以统计所有线程的执行完成的次数, 不包括正在执行的线程
			long completedTaskCount = ((ThreadPoolExecutor) cachedThreadPool).getCompletedTaskCount();
			// 统计所有线程的执行的次数, 包括当前的线程, 所以比completedTaskCount + 1
			long taskCount = ((ThreadPoolExecutor) cachedThreadPool).getTaskCount();
			// 最大线程数量, 为2147483647, 即int类型的最大值范围
			int maximumPoolSize = ((ThreadPoolExecutor) cachedThreadPool).getMaximumPoolSize();
			// 程序执行时线程池中存在过的 最大线程数量, 有参考意义
			int largestPoolSize = ((ThreadPoolExecutor) cachedThreadPool).getLargestPoolSize();
			System.err.println("activeCount>>>" + activeCount);// 获取futureTask的返回值
			System.err.println("poolSize>>>" + poolSize);
			System.err.println("corePoolSize>>>" + corePoolSize);
			System.err.println("completedTaskCount>>>" + completedTaskCount);
			System.err.println("taskCount>>>" + taskCount);
			System.err.println("maximumPoolSize>>>" + maximumPoolSize);
			System.err.println("largestPoolSize>>>" + largestPoolSize);

		}
	}

	/** cachedThreadPool动态创建线程, 线程池中60s未使用的线程会回收 */
	private static void testCacheThread() {
		ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
		for (int i = 0; i < 1000; i++) {
			final int index = i;
			try {
				Thread.sleep(index * 100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Future<?> submit = cachedThreadPool.submit(() -> {
				try {
					Thread.sleep(300);
					System.out.println(Thread.currentThread().getName() + ">>>" + index);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});

			/** 线程池参数获取 */
			// 获取线程池中活跃的线程数, 一般是0条或者1条, 没参考意义
			int activeCount = ((ThreadPoolExecutor) cachedThreadPool).getActiveCount();
			// 线程池中的线程数,包括活跃和不活跃的
			int poolSize = ((ThreadPoolExecutor) cachedThreadPool).getPoolSize();
			// 一直是0,没参考意义
			int corePoolSize = ((ThreadPoolExecutor) cachedThreadPool).getCorePoolSize();
			// 通过线程池中的线程处理完的任务数量, 可以统计所有线程的执行完成的次数, 不包括正在执行的线程
			long completedTaskCount = ((ThreadPoolExecutor) cachedThreadPool).getCompletedTaskCount();
			// 统计所有线程的执行的次数, 包括当前的线程, 所以比completedTaskCount + 1
			long taskCount = ((ThreadPoolExecutor) cachedThreadPool).getTaskCount();
			// 最大线程数量, 为2147483647, 即int类型的最大值范围
			int maximumPoolSize = ((ThreadPoolExecutor) cachedThreadPool).getMaximumPoolSize();
			// 程序执行时线程池中存在过的 最大线程数量, 有参考意义
			int largestPoolSize = ((ThreadPoolExecutor) cachedThreadPool).getLargestPoolSize();
			System.err.println("activeCount>>>" + activeCount);// 获取futureTask的返回值
			System.err.println("poolSize>>>" + poolSize);
			System.err.println("corePoolSize>>>" + corePoolSize);
			System.err.println("completedTaskCount>>>" + completedTaskCount);
			System.err.println("taskCount>>>" + taskCount);
			System.err.println("maximumPoolSize>>>" + maximumPoolSize);
			System.err.println("largestPoolSize>>>" + largestPoolSize);
		}
	}
}
