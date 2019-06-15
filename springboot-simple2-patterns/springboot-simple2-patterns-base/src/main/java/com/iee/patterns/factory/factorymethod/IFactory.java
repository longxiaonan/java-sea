package com.iee.patterns.factory.factorymethod;

import com.iee.patterns.factory.traditional.Operation;

//工厂接口
public interface IFactory {
    Operation CreateOption();
}
