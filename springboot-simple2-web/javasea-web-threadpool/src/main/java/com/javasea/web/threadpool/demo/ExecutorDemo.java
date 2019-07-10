package com.javasea.web.threadpool.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.Executor;

/**
 * @ClassName ExecutorDemo
 * @Description 定时执行某个任务，比如可以定期打印线程的执行日志等。
 * @Author longxiaonan@163.com
 * @Date 2019/7/8 0008 22:53
 */
@Component
public class ExecutorDemo {

    @Autowired
    Executor taskExecutor;

    public void test() {
        taskExecutor.execute(()->System.out.println("task ScheduledExecutorService "+new Date()));
    }
}
