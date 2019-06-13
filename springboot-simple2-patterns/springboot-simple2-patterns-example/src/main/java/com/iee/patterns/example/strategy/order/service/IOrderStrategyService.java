package com.iee.patterns.example.strategy.order.service;

import com.iee.patterns.example.strategy.order.model.OrderDTO;

public interface IOrderStrategyService {
    /**
     * 根据订单的不同类型作出不同的处理
     *
     * @param dto 订单实体
     * @return 为了简单，返回字符串
     */
    String handle(OrderDTO dto);
}
