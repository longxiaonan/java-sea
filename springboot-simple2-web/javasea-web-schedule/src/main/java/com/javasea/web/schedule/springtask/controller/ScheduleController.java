package com.javasea.web.schedule.springtask.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javasea.web.schedule.springtask.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ScheduleDemo
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2019/7/8 0008 21:43
 */
@RestController
public class ScheduleController {

    @Autowired
    TestService service;

    @Autowired
    ObjectMapper objectMapper;

    @GetMapping("/product/num")
    public List<Map<String, String>> test() throws JsonProcessingException {
        int i = service.scheduled2();
        Map map = new HashMap<String,String>();
        map.put("value", i + "");
        List<Map<String,String>> list = new ArrayList<>();
        list.add(map);
        return list;
    }
}
