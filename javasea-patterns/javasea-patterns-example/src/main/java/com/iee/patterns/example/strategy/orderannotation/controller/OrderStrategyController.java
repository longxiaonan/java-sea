package com.iee.patterns.example.strategy.orderannotation.controller;

import com.iee.patterns.example.strategy.orderannotation.model.OrderDTO;
import com.iee.patterns.example.strategy.orderannotation.service.IOrderStrategyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName OrderController
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2019/6/13 0013 22:29
 */
@RequestMapping("/strategy")
@RestController
public class OrderStrategyController {

    @Autowired
    IOrderStrategyService orderStrategyService;

    @GetMapping("/order/{type}")
    public String test(@PathVariable String type){
        OrderDTO od = new OrderDTO();
        od.setType(type);
        return orderStrategyService.handle(od);
    }
}
