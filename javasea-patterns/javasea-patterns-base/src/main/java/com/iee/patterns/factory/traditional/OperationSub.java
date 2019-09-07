package com.iee.patterns.factory.traditional;

//减法
public class OperationSub extends Operation {
    @Override
    public double getResule() {
        return getValue1() - getValue2();
    }
}
