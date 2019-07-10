package com.iee.patterns.example.strategy.orderregister.solver.impl;

import com.iee.patterns.example.strategy.orderregister.common.InspectionConstant;
import com.iee.patterns.example.strategy.orderregister.solver.InspectionSolver;
import org.springframework.stereotype.Component;

@Component
public class ReplaceOrderGoodsSolver extends InspectionSolver {

    @Override
    public void solve(Long orderId, Long userId) {
        System.out.println("订单"+orderId+"开始进行替换商品了");
    }

    @Override
    public String[] supports() {
        return new String[]{InspectionConstant.INSPECTION_TASK_TYPE_BATCH_REPLACE_ORDER_GOODS};
    }
}
