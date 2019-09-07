package com.iee.patterns.decorate;

/**
 * @Description 运行结果：
 * <p>
 * 工作描述：
 * 这周的工作是调试了俩个BUG!!
 * 这周出来的两个BUG比较诡异！经过各种折腾，费尽九牛二虎之类才搞定的，别看只是两个bug，工作量可不少！！
 *
 * 评述：
 * 修复了两个bug！！
 * XXX同学虽然只改两个BUG，但修复这两个BUG意义重大，给他全额奖金!!
 * </p>
 * @Author longxiaonan@163.com
 * @Date 13:31 2019/6/17 0017
 **/
public class TestMain {

    public static void main(String[] args) {
        WorkReport workReport = new RealReport();
        //装饰一下描述
        WorkReport decAddDescript = new DecAddDescript(workReport);
        //装饰一下领导评语，背着领导偷偷加
        WorkReport decACommnet = new DecAddComment(decAddDescript);


        decACommnet.reportData();
        decACommnet.check("修复了两个bug！！");
    }
}
