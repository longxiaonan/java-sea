package com.iee.patterns.example.strategy.orderannotation.service;

import com.iee.patterns.example.strategy.orderannotation.model.OrderDTO;

public interface IOrderStrategyService {
    /**
     * 根据订单的不同类型作出不同的处理
     *
     * @param dto 订单实体
     * @return 为了简单，返回字符串
     */
    String handle(OrderDTO dto);
}
