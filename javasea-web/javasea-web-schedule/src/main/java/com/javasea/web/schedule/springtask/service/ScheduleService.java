package com.javasea.web.schedule.springtask.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @ClassName ScheduleService
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2019/7/8 0008 21:44
 */
@Service
@Slf4j
public class ScheduleService {

    @Autowired
    TaskScheduler taskScheduler;

    @Scheduled(cron = "0/5 * * * * *")
    public void scheduled(){
        log.info("=====>>>>>使用cron  {}",System.currentTimeMillis());
    }
    @Scheduled(fixedRate = 5000)
    public void scheduled1() {
        log.info("=====>>>>>使用fixedRate{}", System.currentTimeMillis());
    }
    @Scheduled(fixedDelay = 5_000)
    public void scheduled2() {
        log.info("=====>>>>>fixedDelay{}",System.currentTimeMillis());
    }

    public void testTaskScheduler(){
        taskScheduler.scheduleWithFixedDelay(()-> System.out.println(222), 5);
    }
}
