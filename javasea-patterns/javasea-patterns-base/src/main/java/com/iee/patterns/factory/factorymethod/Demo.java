package com.iee.patterns.factory.factorymethod;

import com.iee.patterns.factory.traditional.Operation;

/**
 * @ClassName Demo
 * @Description 工厂方法模式(Factory Method Pattern)又称为工厂模式，也叫虚拟构造器(Virtual Constructor)模式
 * 或者多态工厂(Polymorphic Factory)模式，它属于类创建型模式。
 *
 * 工厂方法模式是一种实现了“工厂”概念的面向对象设计模式。就像其他创建型模式一样，它也是处理在不指定对象具体类型的情况下创建对象的问题。
 *
 * 工厂方法模式的实质是“定义一个创建对象的接口，但让实现这个接口的类来决定实例化哪个类。
 *
 * 工厂方法让类的实例化推迟到子类中进行。”
 *
 * 这种工厂方法模式比简单工厂方法模式更加复杂。针对不同的操作（Operation）类都有对应的工厂。
 *
 * 完全符合开闭原则，就是不会影响到其它的工厂创建。
 *
 * 工厂方法模式的主要优点是增加新的产品类时无须修改现有系统，并封装了产品对象的创建细节，系统具有良好的灵活性和可扩展性；
 * 其缺点在于增加新产品的同时需要增加新的工厂，导致系统类的个数成对增加，在一定程度上增加了系统的复杂性。
 *
 * @Author longxiaonan@163.com
 * @Date 2019/6/15 0015 17:59
 */
public class Demo {
    public static void main(String[] args) {
        IFactory factory = new AddFactory();
        Operation operationAdd =  factory.CreateOption();
        operationAdd.setValue1(10);
        operationAdd.setValue2(5);
        System.out.println(operationAdd.getResule());
    }
}
