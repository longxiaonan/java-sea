package com.iee.patterns.example.strategy.orderannotation.handler.handler;

import com.iee.patterns.example.strategy.orderannotation.model.OrderDTO;
import com.iee.patterns.example.strategy.orderannotation.annotation.HandlerType;
import org.springframework.stereotype.Component;

@Component
@HandlerType("3")
public class PromotionHandler extends AbstractHandler {
    @Override
    public String handle(OrderDTO dto) {
        return "处理促销订单";
    }
}
