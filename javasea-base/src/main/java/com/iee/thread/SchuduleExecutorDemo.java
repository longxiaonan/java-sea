package com.iee.thread;

import junit.framework.Assert;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName SchuduleExecutorDemo
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2019/7/22 0022 21:27
 */
@Slf4j
public class SchuduleExecutorDemo {
    /** 固定频率执行任务"忽略"了任务执行的时间，所以能执行更多次数，最终在1秒执行了11次。固定延迟执行任务是在任务完成后再延迟固定的间隔去执行，最终只能在1秒左右的时间执行5次。
     */
    @Test
    public void test1() throws InterruptedException {

        AtomicInteger scheduleAtFixedRateTotal = new AtomicInteger();
        ScheduledExecutorService scheduleAtFixedRateExecutorService = Executors.newSingleThreadScheduledExecutor();
        ScheduledFuture scheduleAtFixedRateTotalFuture = scheduleAtFixedRateExecutorService.scheduleAtFixedRate(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("scheduleAtFixedRate:" + scheduleAtFixedRateTotal.incrementAndGet());
        }, 0, 100, TimeUnit.MILLISECONDS);
        //执行11此，因为虽然延时200毫秒
        //什么？单元测试还是通过的？难道不是应该运行了5次就结束了吗？注意观察，
        //我们的线程池并没有在1秒后结束，而是维持了2秒多，还是老问题，因为是单线程，我们的关闭线程池的那个任务也无法及时得到执行。
        scheduleAtFixedRateExecutorService.schedule(() -> scheduleAtFixedRateTotalFuture.cancel(false), 1, TimeUnit.SECONDS);
        /** 尝试修改代码，把cancel任务放到独立的线程池去执行,  */
//        Executors.newSingleThreadScheduledExecutor().schedule(() -> scheduleAtFixedRateTotalFuture.cancel(false), 1, TimeUnit.SECONDS);
        while (!scheduleAtFixedRateTotalFuture.isDone()) TimeUnit.MILLISECONDS.sleep(1);
        Assert.assertEquals(11, scheduleAtFixedRateTotal.get());


        AtomicInteger scheduleWithFixedDelayTotal = new AtomicInteger();
        ScheduledExecutorService scheduleWithFixedDelayExecutorService = Executors.newSingleThreadScheduledExecutor();
        ScheduledFuture scheduleWithFixedDelayFuture = scheduleWithFixedDelayExecutorService.scheduleWithFixedDelay(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("scheduleWithFixedDelay:" + scheduleWithFixedDelayTotal.incrementAndGet());
        }, 0, 100, TimeUnit.MILLISECONDS);
        scheduleWithFixedDelayExecutorService.schedule(() -> scheduleWithFixedDelayFuture.cancel(false), 1, TimeUnit.SECONDS);
        while (!scheduleWithFixedDelayFuture.isDone()) TimeUnit.MILLISECONDS.sleep(1);
        Assert.assertEquals(5, scheduleWithFixedDelayTotal.get());
    }
}
