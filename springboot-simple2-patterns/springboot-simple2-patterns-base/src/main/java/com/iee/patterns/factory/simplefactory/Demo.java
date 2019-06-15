package com.iee.patterns.factory.simplefactory;

import com.iee.patterns.factory.traditional.Operation;

/**
 * @ClassName Demo
 * @Description 简单工厂模式。当我们需要增加一种计算时，例如开平方。这个时候我们需要先定义一个类继承Operation类，
 * 其中实现平方的代码。除此之外我们还要修改OperationFactory类的代码，增加一个case。
 * 这显然是违背开闭原则的。可想而知对于新产品的加入，工厂类是很被动的。
 * @Author longxiaonan@163.com
 * @Date 2019/6/15 0015 17:53
 */
public class Demo {
    public static void main(String[] args) {
        /** 根据添加创建实体 */
        Operation operationAdd = OperationFactory.createOperation("+");
        operationAdd.setValue1(10);
        operationAdd.setValue2(5);
        System.out.println(operationAdd.getResule());
    }
}
