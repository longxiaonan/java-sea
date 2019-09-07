package com.iee.patterns.factory.factorymethod;

import com.iee.patterns.factory.traditional.Operation;
import com.iee.patterns.factory.traditional.OperationDiv;

//除法类工厂
public class DivFactory implements IFactory {
    public Operation CreateOption() {
        return new OperationDiv();
    }
}
