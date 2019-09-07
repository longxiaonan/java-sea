package com.iee.thread.future;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @ClassName futureDemo
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2019/7/23 0023 15:39
 */
public class FutureDemo {
    public static void main(String[] args) throws Exception {
        Callable<Long> callable = new Callable<Long>() {
            @Override
            public Long call() throws Exception {

                long start = System.currentTimeMillis();
                Thread.sleep(100);
                long end = System.currentTimeMillis();

                long seed = end - start;
                System.out.println("seed=" + seed);

                return seed;
            }
        };

        List<Callable<Long>> tasks = new ArrayList<>();
        tasks.add(callable);
        tasks.add(callable);
        tasks.add(callable);
        tasks.add(callable);
        tasks.add(callable);
        tasks.add(callable);
        tasks.add(callable);
        tasks.add(callable);
        tasks.add(callable);
        tasks.add(callable);

        //获取cpu核数来创建线程
        int poolSize = Runtime.getRuntime().availableProcessors();
        System.out.println("poolSize=" + poolSize);
        ExecutorService executorService = Executors.newFixedThreadPool(poolSize);
        List<Future<Long>> futures = executorService.invokeAll(tasks);
        long result = 0;

        //方法用户返回计算结果，如果计算还没有完成，则在get的时候会进行阻塞，直到获取到结果为止。
        //get(long timeout, TimeUnit unit)方法的耐心是有限的，如果在指定时间内没有完成计算，则会抛出TimeoutException.
        for (Future<Long> future : futures) {
            result += future.get();
        }
        System.out.println("result=" + result);
        executorService.shutdown();
    }

}
