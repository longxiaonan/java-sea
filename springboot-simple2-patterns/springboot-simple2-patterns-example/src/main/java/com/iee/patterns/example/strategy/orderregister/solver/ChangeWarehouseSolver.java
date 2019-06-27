package com.iee.patterns.example.strategy.orderregister.solver;

import com.iee.patterns.example.strategy.orderregister.common.InspectionConstant;
import org.springframework.stereotype.Component;

@Component
public class ChangeWarehouseSolver extends InspectionSolver {

    @Override
    public void solve(Long orderId, Long userId) {
        System.out.println("订单"+orderId+"开始进行批量转仓了。。");
    }

    @Override
    public String[] supports() {
        return new String[] {InspectionConstant.INSPECTION_TASK_TYPE_BATCH_CHANGE_WAREHOUSE};
    }
}
