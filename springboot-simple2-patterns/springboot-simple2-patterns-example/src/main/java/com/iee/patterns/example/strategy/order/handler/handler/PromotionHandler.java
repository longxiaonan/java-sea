package com.iee.patterns.example.strategy.order.handler.handler;

import com.iee.patterns.example.strategy.order.model.OrderDTO;
import com.iee.patterns.example.strategy.order.annotation.HandlerType;
import org.springframework.stereotype.Component;

@Component
@HandlerType("3")
public class PromotionHandler extends AbstractHandler {
    @Override
    public String handle(OrderDTO dto) {
        return "处理促销订单";
    }
}
