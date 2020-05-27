package com.iee.file.upload.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;

@Slf4j
@Component
@Configuration
public class TaskExecutePool {

    private static final int CORE_SIZE = 4;
    private static final int MAX_SIZE = 8;
    private static final int QUEUE_CAPACITY = 500;
    private static final int KEEP_ALIVE = 120;
    private static final boolean IS_DAEMON = false;
    private static final boolean ALLOW_THREAD_TIMEOUT = false;

    @Bean
    public ThreadPoolTaskExecutor taskAsyncPool() {

        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(CORE_SIZE);
        taskExecutor.setMaxPoolSize(MAX_SIZE);
        taskExecutor.setQueueCapacity(QUEUE_CAPACITY);
        taskExecutor.setKeepAliveSeconds(KEEP_ALIVE);
        taskExecutor.setDaemon(IS_DAEMON);
        taskExecutor.setAllowCoreThreadTimeOut(ALLOW_THREAD_TIMEOUT);
        taskExecutor.setThreadNamePrefix("pool-thread-");
        taskExecutor.setRejectedExecutionHandler(new CallerRunsPolicy());

        log.info("thread pool: {} 初始化成功，当前维持核心线程数量为：{}", "pool-thread", 4);
        return taskExecutor;
    }

    public void execute(Runnable task) {

        taskAsyncPool().execute(task);
    }

    public Future<?> submit(Runnable task) {

        return taskAsyncPool().submit(task);
    }

    public <T> Future<T> submit(Callable<T> task) {

        return taskAsyncPool().submit(task);
    }
}
