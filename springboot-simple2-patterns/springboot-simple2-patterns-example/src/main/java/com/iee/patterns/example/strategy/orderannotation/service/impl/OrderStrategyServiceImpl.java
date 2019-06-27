package com.iee.patterns.example.strategy.orderannotation.service.impl;

import com.iee.patterns.example.strategy.orderannotation.model.OrderDTO;
import com.iee.patterns.example.strategy.orderannotation.service.IOrderStrategyService;

/**
 * @Description 传统方式实现
 * @Author longxiaonan@163.com
 * @Date 22:03 2019/6/13 0013
 **/
//@Service
public class OrderStrategyServiceImpl implements IOrderStrategyService {
    @Override
    public String handle(OrderDTO dto) {
        String type = dto.getType();
        if ("1".equals(type)) {
            return "处理普通订单";
        } else if ("2".equals(type)) {
            return "处理团购订单";
        } else if ("3".equals(type)) {
            return "处理促销订单";
        }
        return null;
    }
}
