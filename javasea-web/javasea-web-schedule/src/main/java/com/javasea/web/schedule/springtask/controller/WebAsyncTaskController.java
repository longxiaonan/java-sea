package com.javasea.web.schedule.springtask.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

/**
 * WebAsyncTask
 *
 * @author ijiangtao
 * @create 2019-07-03 11:31
 **/
@RestController
public class WebAsyncTaskController {

    private Map<String, String> buildResult() {
        System.out.println("building result");
        Map<String, String> result = new HashMap<>();
        try {
            Thread.sleep(3 * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 1 * 1000; i++) {
            result.put(i + "-key", i + "value");
        }
        return result;
    }

    private void doTask() {
        System.out.println("do some tasks");
        try {
            Thread.sleep(3 * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @GetMapping("/r1")
    public Map<String, String> r1() {

        Instant now = Instant.now();

        Map<String, String> result = buildResult();

        doTask();

        System.out.println("r1 time consumption: " + ChronoUnit.SECONDS.between(now, Instant.now()) + " seconds");

        return result;
    }
}

