package com.javasea.web.schedule.springtask.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName ScheduleService
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2019/7/8 0008 21:44
 */
@Service
@Slf4j
public class TestService {

    private static AtomicInteger count = new AtomicInteger(100);

//    @Scheduled(cron = "0/5 * * * * *")
//    public void scheduled(){
//        log.info("=====>>>>>使用cron  {}",System.currentTimeMillis());
//    }
//    @Scheduled(fixedRate = 5000)
//    public void scheduled1() {
//        log.info("=====>>>>>使用fixedRate{}", System.currentTimeMillis());
//    }
//    @Scheduled(fixedDelay = 5_000)
    public int scheduled2() {
//        log.info("=====>>>>>fixedDelay{}",System.currentTimeMillis());

        if(count.intValue() > 500){
            count.lazySet(100);
        }
        return count.getAndIncrement();

    }
}
