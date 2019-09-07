package com.iee.patterns.decorate;

/**
 * @Description 装饰抽象类，持有工作报告的引用，扩展报告。
 * @Author longxiaonan@163.com
 * @Date 13:29 2019/6/17 0017
 **/
public abstract class Decorator extends  WorkReport {

    protected WorkReport workReport;

    public Decorator(WorkReport workReport){
        this.workReport = workReport;
    }
}
