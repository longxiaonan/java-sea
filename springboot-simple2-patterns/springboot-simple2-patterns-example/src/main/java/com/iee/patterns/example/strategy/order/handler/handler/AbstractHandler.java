package com.iee.patterns.example.strategy.order.handler.handler;

import com.iee.patterns.example.strategy.order.model.OrderDTO;

public abstract class AbstractHandler {
    abstract public String handle(OrderDTO dto);
}
