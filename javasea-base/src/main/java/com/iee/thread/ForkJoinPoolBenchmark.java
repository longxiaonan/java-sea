package com.iee.thread;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.util.StopWatch;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.LongStream;

@Slf4j
public class ForkJoinPoolBenchmark {
    /** forJoin每个线程一个线程池，增加性能，防止冲突 */
    @Test
    public void test() throws InterruptedException {
        AtomicLong atomicLong = new AtomicLong();
        StopWatch stopWatch = new StopWatch();
        ExecutorService normal = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        ExecutorService forkjoin = Executors.newWorkStealingPool(Runtime.getRuntime().availableProcessors());

        stopWatch.start("normal");
        LongStream.rangeClosed(1, 10000000).forEach(__->normal.submit(atomicLong::incrementAndGet));
        normal.shutdown();
        normal.awaitTermination(1, TimeUnit.HOURS);
        stopWatch.stop();
        long r = atomicLong.get();
        stopWatch.start("forkjoin");
        LongStream.rangeClosed(1, 10000000).forEach(__->forkjoin.submit(atomicLong::incrementAndGet));
        forkjoin.shutdown();
        forkjoin.awaitTermination(1, TimeUnit.HOURS);
        stopWatch.stop();
        log.info(stopWatch.prettyPrint());
        log.info("result:{},{}", r, atomicLong.get());
    }
}
