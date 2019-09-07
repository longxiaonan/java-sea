package com.iee.thread;

import junit.framework.Assert;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@Slf4j
public class TomcatThreadPoolTest {

//    @Test
//    public void SortTest() throws InterruptedException {
//        TomcatTaskQueue taskqueue = new TomcatTaskQueue(5);
//        TomcatThreadPool threadPool = new TomcatThreadPool(2, 5, 60, TimeUnit.SECONDS, taskqueue);
//        taskqueue.setParent(threadPool);
//        IntStream.rangeClosed(1, 10).forEach(i -> threadPool.execute(new Task(true, i)));
//        IntStream.rangeClosed(1, 10).forEach(i -> threadPool.execute(new Task(false, i)
//                , 1050, TimeUnit.MILLISECONDS));
//
//        threadPool.shutdown();
//        threadPool.awaitTermination(1, TimeUnit.HOURS);
//    }

    @ToString
    class Task implements Runnable {
        private boolean slow;
        private String name;

        public Task(boolean slow, int index) {
            this.slow = slow;

            this.name = String.format("%s-%d", slow ? "slow" : "quick", index);
        }

        @Override
        public void run() {
            log.info("Start:{}", name);
            if (slow) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            log.info("Finish:{}", name);
        }
    }
}
