package com.iee.patterns.example.strategy.orderannotation.service.impl;

import com.iee.patterns.example.strategy.orderannotation.model.OrderDTO;
import com.iee.patterns.example.strategy.orderannotation.service.IOrderStrategyService;
import com.iee.patterns.example.strategy.orderannotation.handler.HandlerContext;
import com.iee.patterns.example.strategy.orderannotation.handler.handler.AbstractHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** 使用策略模式实现 */

@Service
public class OrderStrategyServiceV2Impl implements IOrderStrategyService {
    //处理器上下文，用来保存不同的业务处理器
    @Autowired
    private HandlerContext handlerContext;
    @Override
    public String handle(OrderDTO dto) {
        AbstractHandler handler = handlerContext.getInstance(dto.getType());
        return handler.handle(dto);
    }
}
