package com.iee.patterns.decorate;
/**
 * @Description 工作报告
 * @Author longxiaonan@163.com
 * @Date 13:28 2019/6/17 0017
 **/
public class RealReport extends WorkReport {

    @Override
    public void reportData() {
        System.out.println("工作描述：");
        System.out.println("这周的工作是调试了俩个BUG!!");
    }

    @Override
    public void check(String comment) {
        System.out.println("评述：");
        System.out.println(comment);
    }
}
