package com.iee.thread;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * @ClassName ThreadPoolTest
 * @Description
 * 阿里Java开发手册不建议使用这两种预定义的线程池，我们应该自己根据需要控制线程池的：
 * 队列类型
 * 队列长度
 * 核心线程数
 * 最大线程数
 * 回收时间
 * 是否回收核心线程
 * 是否预创建核心线程
 * 拒绝处理方式
 * 线程工厂
 * @Author longxiaonan@163.com
 * @Date 2019/7/22 0022 18:55
 */
@Slf4j
public class ThreadPoolTest {
    //在Test1上,加大core线程数,到达50后，放入队列，排队执行，队列满了(如果真的到达最大值能满的话)，开始增加最大线程数，最大到100
    @Test
    public void test3() throws InterruptedException {
        AtomicInteger atomicInteger = new AtomicInteger();
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(50, 100,
                30, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>());
        threadPool.allowCoreThreadTimeOut(true);


    }


    // 暴力方式修改Test1，实现超过core线程数后，继续增加活跃线程数，最大到5，然后再放到队列
    @Test
    public void test2() throws InterruptedException {
        AtomicInteger atomicInteger = new AtomicInteger();

        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>(10) {
            @Override
            public boolean offer(Runnable e) {
                return false;
            }
        };

        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                2, 5,
                5, TimeUnit.SECONDS,
                queue, new NamedThreadFactory("lxn"), (r, executor) -> {
                    try {
                        executor.getQueue().put(r);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
        });

        threadPool.allowCoreThreadTimeOut(true);
        printStats(threadPool);
        submitTasks(atomicInteger,threadPool);
        threadPool.shutdown();
        threadPool.awaitTermination(1, TimeUnit.HOURS);
    }

    /** 默认的线程池工作方式，开始创建的线程数是2，然后丢到队列，队列满后再增加活跃线程数，直到最大值5
     **/
    @Test
    public void test1() throws InterruptedException {
        AtomicInteger atomicInteger = new AtomicInteger();
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                2, 5,
                5, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(10));

        printStats(threadPool);
        submitTasks(atomicInteger,threadPool);
        threadPool.shutdown();
        threadPool.awaitTermination(1, TimeUnit.HOURS);
    }

    private void printStats(ThreadPoolExecutor threadPool){
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            log.info("=========================");
            log.info("Pool Size: {}", threadPool.getPoolSize());
            log.info("Active Threads: {}", threadPool.getActiveCount());
            log.info("Number of Tasks Completed: {}", threadPool.getCompletedTaskCount());
            log.info("Number of Tasks in Queue: {}", threadPool.getQueue().size());

            log.info("=========================");
        }, 0, 1, TimeUnit.SECONDS);
    }

    private void submitTasks(AtomicInteger atomicInteger, ThreadPoolExecutor threadPool) {
        IntStream.rangeClosed(1, 20).forEach(i -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int id = atomicInteger.incrementAndGet();
            threadPool.submit(() -> {
                log.info("{} started", id);
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("{} finished", id);
            });
        });
    }
}
