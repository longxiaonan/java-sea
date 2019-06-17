package com.iee.patterns.decorate;

/**
 * @Description 具体的装饰类，扩展领导审核功能
 * @Author longxiaonan@163.com
 * @Date 13:30 2019/6/17 0017
 **/
public class DecAddComment extends Decorator {

    public DecAddComment(WorkReport workReport) {
        super(workReport);
    }

    @Override
    public void reportData() {
        workReport.reportData();
    }

    @Override
    public void check(String comment) {
        workReport.check(comment);
        ////动态扩展，悄悄把领导的评语后面增加一些内容
        System.out.println("XXX同学虽然只改两个BUG，但修复这两个BUG意义重大，给他全额奖金!!");
    }
}
