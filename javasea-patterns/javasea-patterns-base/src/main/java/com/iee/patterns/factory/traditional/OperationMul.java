package com.iee.patterns.factory.traditional;

//乘法
public class OperationMul extends Operation {
    @Override
    public double getResule() {
        return getValue1() * getValue2();
    }
}
