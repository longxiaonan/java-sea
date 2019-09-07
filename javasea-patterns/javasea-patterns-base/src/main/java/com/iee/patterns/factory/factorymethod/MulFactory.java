package com.iee.patterns.factory.factorymethod;

import com.iee.patterns.factory.traditional.Operation;
import com.iee.patterns.factory.traditional.OperationMul;

//除法类工厂
public class MulFactory implements IFactory {
    public Operation CreateOption() {
        return new OperationMul();
    }
}
