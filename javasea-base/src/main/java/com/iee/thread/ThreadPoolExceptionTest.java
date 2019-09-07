package com.iee.thread;

import junit.framework.Assert;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;

@Slf4j
public class ThreadPoolExceptionTest {

    @Before
    public void setDefaultUncaughtExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler((Thread t, Throwable e) -> {
            log.warn("Exception in thread {}", t, e);
        });
    }

    /** 比较execute和submit两种异常的处理效率，执行结果如下：
     * -----------------------------------------
     * ms     %     Task name
     * -----------------------------------------
     * 49190  099%  execute
     * 00353  001%  submit
     * @throws InterruptedException
     */
    @Test
    public void test3() throws InterruptedException {
        StopWatch stopWatch = new StopWatch();
        ExecutorService threadPool1 = Executors.newFixedThreadPool(1);
        stopWatch.start("execute");
        IntStream.rangeClosed(1, 100000).forEach(i->threadPool1.execute(()->{
            throw new RuntimeException("error");
        }));
        threadPool1.shutdown();
        threadPool1.awaitTermination(1, TimeUnit.HOURS);
        stopWatch.stop();
        ExecutorService threadPool2 = Executors.newFixedThreadPool(1);
        stopWatch.start("submit");
        IntStream.rangeClosed(1, 100000).forEach(i->threadPool2.submit(()->{
            throw new RuntimeException("error");
        }));
        threadPool2.shutdown();
        threadPool2.awaitTermination(1, TimeUnit.HOURS);
        stopWatch.stop();
        log.info(stopWatch.prettyPrint());
    }

    /** 不会中断线程执行，在future.get()的时候抛出异常 */
    @Test
    public void test2() throws InterruptedException {
        String prefix = "SortTest";
        ExecutorService threadPool = Executors.newFixedThreadPool(1, new NamedThreadFactory(prefix));
        List<Future> futures = new ArrayList<>();
        IntStream.rangeClosed(1, 10).forEach(i -> futures.add(threadPool.submit(() -> {
            if (i == 5) throw new RuntimeException("error");
            log.info("I'm done : {}", i);
//            if (i < 5) Assert.assertEquals(prefix + "1", Thread.currentThread().getName());
//            else Assert.assertEquals(prefix + "2", Thread.currentThread().getName());
        })));

        for (Future future : futures) {
            try {
                future.get();
            } catch (ExecutionException e) {
                log.warn("future ExecutionException",e);
            }
        }
        threadPool.shutdown();
        threadPool.awaitTermination(1, TimeUnit.HOURS);
    }

    /** execute发生异常会中断线程执行 */
    @Test
    public void test() throws InterruptedException {
        String prefix = "SortTest";
        ExecutorService threadPool = Executors.newFixedThreadPool(1, new NamedThreadFactory(prefix));
        IntStream.rangeClosed(1, 10).forEach(i -> threadPool.execute(() -> {
            if (i == 5) throw new RuntimeException("error");
            log.info("I'm done : {}", i);
            if (i < 5) {
                Assert.assertEquals(prefix + "1", Thread.currentThread().getName());
            } else {
                Assert.assertEquals(prefix + "2", Thread.currentThread().getName());
            }
        }));

        threadPool.shutdown();
        threadPool.awaitTermination(1, TimeUnit.HOURS);
    }
}
