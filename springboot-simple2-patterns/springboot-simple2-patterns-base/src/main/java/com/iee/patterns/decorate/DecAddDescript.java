package com.iee.patterns.decorate;

/**
 * @Description 具体的装饰类，扩展查看报告方法
 * @Author longxiaonan@163.com
 * @Date 13:29 2019/6/17 0017
 **/
public class DecAddDescript extends Decorator{

    public DecAddDescript(WorkReport workReport) {
        super(workReport);
    }

    @Override
    public void reportData() {
        //动态扩展,增加一些描述
        super.workReport.reportData();
        System.out.println("这周出来的两个BUG比较诡异！经过各种折腾，费尽九牛二虎之类才搞定的，别看只是两个bug，工作量可不少！！");
        System.out.println();
    }

    @Override
    public void check(String comment) {
        super.workReport.check(comment);
    }
}
