package com.iee.patterns.factory.abstractfactory;

import com.iee.patterns.factory.abstractfactory.car.BenzCar;
import com.iee.patterns.factory.abstractfactory.car.TeslaCar;
import com.iee.patterns.factory.abstractfactory.factory.BusinessCarFactory;
import com.iee.patterns.factory.abstractfactory.factory.CarFactory;
import com.iee.patterns.factory.abstractfactory.factory.SportCarFactory;

/**
 * @ClassName Demo
 * @Description 抽象工厂模式(Abstract Factory Pattern)：提供一个创建一系列相关或相互依赖对象的接口，而无须指定它们具体的类。
 * 抽象工厂模式又称为Kit模式，属于对象创建型模式。
 *
 * 抽象工厂模式提供了一种方式，可以将同一产品族的单独的工厂封装起来。在正常使用中，客户端程序需要创建抽象工厂的具体实现，
 * 然后使用抽象工厂作为接口来创建这一主题的具体对象。客户端程序不需要知道（或关心）它从这些内部的工厂方法中获得对象的具体类型，
 * 因为客户端程序仅使用这些对象的通用接口。
 * 抽象工厂模式将一组对象的实现细节与他们的一般使用分离开来。
 *
 * 抽象工厂模式的主要优点是隔离了具体类的生成，使得客户并不需要知道什么被创建，而且每次可以通过具体工厂类创建一个产品族中的多个对象，
 * 增加或者替换产品族比较方便，增加新的具体工厂和产品族很方便；主要缺点在于增加新的产品等级结构很复杂，
 * 需要修改抽象工厂和所有的具体工厂类，对“开闭原则”的支持呈现倾斜性。
 *
 * AbstractFactory(抽象工厂)：用于声明生成抽象产品的方法
 *
 * ConcreteFactory(具体工厂)：实现了抽象工厂声明的生成抽象产品的方法，生成一组具体产品，这些产品构成了一个产品族，
 * 每一个产品都位于某个产品等级结构中；
 *
 * AbstractProduct(抽象产品)：为每种产品声明接口，在抽象产品中定义了产品的抽象业务方法；
 *
 * Product(具体产品)：定义具体工厂生产的具体产品对象，实现抽象产品接口中定义的业务方法。
 * 采用一个汽车代工厂造汽车的例子。假设我们是一家汽车代工厂商，我们负责给奔驰和特斯拉两家公司制造车子。
 *
 * 我们简单的把奔驰车理解为需要加油的车，特斯拉为需要充电的车。其中奔驰车中包含跑车和商务车两种，特斯拉同样也包含奔驰车和商务车。
 *
 * 以上场景，我们就可以把跑车和商务车分别对待，对于跑车有单独的工厂创建，商务车也有单独的工厂。
 *
 * 这样，以后无论是再帮任何其他厂商造车，只要是跑车或者商务车我们都不需要再引入工厂。同样，如果我们要增加一种其他类型的车，
 * 比如越野车，我们也不需要对跑车或者商务车的任何东西做修改。
 *
简单工厂 ：用来生产同一等级结构中的任意产品。（对于增加新的产品，主要是新增产品，就要修改工厂类。符合单一职责原则。不符合开放-封闭原则）

工厂方法 ：用来生产同一等级结构中的固定产品。（支持增加任意产品，新增产品时不需要更改已有的工厂，需要增加该产品对应的工厂。
符合单一职责原则、符合开放-封闭原则。但是引入了复杂性）

抽象工厂 ：用来生产不同产品族的全部产品。（增加新产品时，需要修改工厂，增加产品族时，需要增加工厂。
符合单一职责原则，部分符合开放-封闭原则，降低了复杂性）

 * @Author longxiaonan@163.com
 * @Date 2019/6/15 0015 19:30
 */
public class Demo {
    public static void main(String[] args) {
        CarFactory sportCarFactory = new SportCarFactory();
        BenzCar benzCar = sportCarFactory.getBenzCar();
        TeslaCar teslaCar = sportCarFactory.getTeslaCar();

        BusinessCarFactory businessCarFactory = new BusinessCarFactory();
        BenzCar benzCar1 = businessCarFactory.getBenzCar();
        TeslaCar teslaCar1 = businessCarFactory.getTeslaCar();

        benzCar.gasUp();
        teslaCar.charge();
        benzCar1.gasUp();
        teslaCar1.charge();
    }
}
