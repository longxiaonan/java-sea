package com.zhirui.lmwy.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/** 定时器工具类，用于定时打印一些log */
@Slf4j
public class TimeScheduler {
    private static final TimeScheduler instance_ = new TimeScheduler();

    private ScheduledExecutorService scheduler_ = null;
    private HashMap<String, ScheduledFuture<?>> taskHandleMap_ = new HashMap<String, ScheduledFuture<?>>();

    // 是否已经调用了start方法进行了初始化，因为在CDI中，要启动线程，如果在服务中已经启动了线程，那就不能重新进行初始化
    boolean isInvokeStartMethod = false;

    public TimeScheduler() {
    }

    /**
     * 直接改成单例的饱汉模式 modify by lijian 2014-07-09
     *
     * @return
     */
    public static TimeScheduler instance() {
        if (!instance_.isStarted()) {
            instance_.start(2);
        }
        return instance_;
    }

    public boolean isStarted() {
        return isInvokeStartMethod;
    }

    public synchronized int start(int tpNum) {

        if (isInvokeStartMethod) {
            log.info("has Start time scheduler");
            return 0;
        }

        log.info("Start time scheduler, tpNum[{}]", tpNum);
        this.scheduler_ = Executors.newScheduledThreadPool(tpNum);

        isInvokeStartMethod = true;

        return 0;
    }

    public String registerScheduledTask(Runnable command, long delay, long period, TimeUnit unit) {
        String taskHandleKey = UUID.randomUUID().toString();
        //固定时间后执行，不管有没执行完
        ScheduledFuture<?> taskHanlde = this.scheduler_.scheduleAtFixedRate(command, delay, period, unit);
        this.taskHandleMap_.put(taskHandleKey, taskHanlde);

        log.info("Register scheduled vlr successfully, taskHandleKey[{}], delay[{}], period[{}]",
                taskHandleKey, delay, period);

        return taskHandleKey;
    }

    public void cancelScheduledTask(String taskHandleKey) {
        ScheduledFuture<?> taskHanlde = this.taskHandleMap_.get(taskHandleKey);
        if (null != taskHanlde) {
            taskHanlde.cancel(true);
            this.taskHandleMap_.remove(taskHandleKey);
            log.info("Cancel scheduled vlr successfully, taskHandleKey[{}]", taskHandleKey);
        }
    }

    public static void main(String[] args) {
        String key = TimeScheduler.instance().registerScheduledTask(() -> {
            log.info("当前线程数为:[{}],峰值达到过的最大的线程数为:[{}],配置的最大线程数为:[{}]");
        }, 20, 2, TimeUnit.SECONDS);

        System.out.println(TimeScheduler.instance().isStarted());
//        TimeScheduler.instance().cancelScheduledTask(key);
    }
}
