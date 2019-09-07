package com.iee.patterns.factory.abstractfactory.car;

public class TeslaSportCar implements TeslaCar {
    public void charge() {
        System.out.println("给我特斯拉跑车冲满电");
    }
}
