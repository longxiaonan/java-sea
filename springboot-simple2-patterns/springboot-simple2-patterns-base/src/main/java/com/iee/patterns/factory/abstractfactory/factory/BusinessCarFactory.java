package com.iee.patterns.factory.abstractfactory.factory;

import com.iee.patterns.factory.abstractfactory.car.BenzBusinessCar;
import com.iee.patterns.factory.abstractfactory.car.BenzCar;
import com.iee.patterns.factory.abstractfactory.car.TeslaBusinessCar;
import com.iee.patterns.factory.abstractfactory.car.TeslaCar;

public class BusinessCarFactory implements CarFactory {
    public BenzCar getBenzCar() {
        return new BenzBusinessCar();
    }

    public TeslaCar getTeslaCar() {
        return new TeslaBusinessCar();
    }
}
