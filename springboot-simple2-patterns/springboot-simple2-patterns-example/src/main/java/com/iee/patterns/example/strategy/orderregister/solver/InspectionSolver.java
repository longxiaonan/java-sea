package com.iee.patterns.example.strategy.orderregister.solver;
/**
 * @Description 抽象业务处理器
 * @Author longxiaonan@163.com
 * @Date 19:45 2019/6/27 0027
 **/
public abstract class InspectionSolver {

    public abstract void solve(Long orderId, Long userId);

    public abstract String[] supports();
}
