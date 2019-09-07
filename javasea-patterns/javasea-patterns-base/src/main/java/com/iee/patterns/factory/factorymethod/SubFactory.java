package com.iee.patterns.factory.factorymethod;

import com.iee.patterns.factory.traditional.Operation;
import com.iee.patterns.factory.traditional.OperationSub;

//减法类工厂
public class SubFactory implements IFactory {
    public Operation CreateOption() {
        return new OperationSub();
    }
}
