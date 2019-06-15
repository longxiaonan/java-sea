package com.iee.patterns.factory.traditional;

/**
 * @ClassName Demo
 * @Description 当我需要执行减法运算时，我就要创建一个OperationSub类。
 * 也就是说，我想要使用不同的运算的时候就要创建不同的类，并且要明确知道该类的名字。
 *
 * 那么这种重复的创建类的工作其实可以放到一个统一的工厂类中。简单工厂模式有以下优点：
 * 1、一个调用者想创建一个对象，只要知道其名称就可以了。
 *
 * 2、屏蔽产品的具体实现，调用者只关心产品的接口。
 *
 * 这里是演示不用工厂模式时的传统方式创建对象。
 * @Author longxiaonan@163.com
 * @Date 2019/6/14 0014 11:25
 */
public class Demo {
    public static void main(String[] args) {
        OperationAdd operationAdd = new OperationAdd();
        operationAdd.setValue1(10);
        operationAdd.setValue2(5);
        System.out.println(operationAdd.getResule());

        OperationMul operationMul = new OperationMul();
        operationMul.setValue1(10);
        operationMul.setValue2(5);
        System.out.println(operationMul.getResule());
    }
}
