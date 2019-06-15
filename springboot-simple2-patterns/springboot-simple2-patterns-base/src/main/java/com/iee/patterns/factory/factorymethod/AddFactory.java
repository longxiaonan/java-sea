package com.iee.patterns.factory.factorymethod;

import com.iee.patterns.factory.traditional.Operation;
import com.iee.patterns.factory.traditional.OperationAdd;

//加法类工厂
public class AddFactory implements IFactory {
    public Operation CreateOption() {
        return new OperationAdd();
    }
}
