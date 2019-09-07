package com.iee.patterns.factory.abstractfactory.factory;

import com.iee.patterns.factory.abstractfactory.car.BenzCar;
import com.iee.patterns.factory.abstractfactory.car.TeslaCar;

public interface CarFactory {

    public BenzCar getBenzCar();
    public TeslaCar getTeslaCar();
}
