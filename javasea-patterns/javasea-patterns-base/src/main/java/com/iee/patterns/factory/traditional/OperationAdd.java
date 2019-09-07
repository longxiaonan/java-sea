package com.iee.patterns.factory.traditional;

//加法
public class OperationAdd extends Operation {
    @Override
    public double getResule() {
        return getValue1() + getValue2();
    }
}
