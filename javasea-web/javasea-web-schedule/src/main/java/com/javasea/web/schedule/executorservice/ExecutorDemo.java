package com.javasea.web.schedule.executorservice;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName ExecutorDemo
 * @Description 定时执行某个任务，比如可以定期打印线程的执行日志等。
 * @Author longxiaonan@163.com
 * @Date 2019/7/8 0008 22:53
 */
public class ExecutorDemo {
    public static void main(String[] args) {
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        // 参数：1、任务体 2、首次执行的延时时间
        //      3、任务执行间隔 4、间隔时间单位
        service.scheduleAtFixedRate(()->System.out.println("task ScheduledExecutorService "+new Date()),
                0, 3, TimeUnit.SECONDS);
    }
}
