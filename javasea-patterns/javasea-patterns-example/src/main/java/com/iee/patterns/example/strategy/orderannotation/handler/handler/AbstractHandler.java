package com.iee.patterns.example.strategy.orderannotation.handler.handler;

import com.iee.patterns.example.strategy.orderannotation.model.OrderDTO;

public abstract class AbstractHandler {
    abstract public String handle(OrderDTO dto);
}
