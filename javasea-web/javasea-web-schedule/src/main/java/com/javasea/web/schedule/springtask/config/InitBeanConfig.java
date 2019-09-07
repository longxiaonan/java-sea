package com.javasea.web.schedule.springtask.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * @ClassName InitBeanConfig
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2019/8/22 0022 10:22
 */
@Configuration
public class InitBeanConfig {

    /**
     * 配置 Schedule 的线程池.
     */
    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(10);
        taskScheduler.setThreadNamePrefix("springboot-task");
        return taskScheduler;
    }
}
