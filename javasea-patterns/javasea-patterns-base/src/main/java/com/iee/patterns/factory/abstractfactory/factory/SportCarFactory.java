package com.iee.patterns.factory.abstractfactory.factory;

import com.iee.patterns.factory.abstractfactory.car.BenzCar;
import com.iee.patterns.factory.abstractfactory.car.BenzSportCar;
import com.iee.patterns.factory.abstractfactory.car.TeslaCar;
import com.iee.patterns.factory.abstractfactory.car.TeslaSportCar;

public class SportCarFactory implements CarFactory {
    public BenzCar getBenzCar() {
        return new BenzSportCar();
    }

    public TeslaCar getTeslaCar() {
        return new TeslaSportCar();
    }
}
