package com.iee.lambda.predicate;

import java.util.function.Predicate;

/**
 * @ClassName predicate
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2018/11/2 0002 21:41
 */
public class predicate{
        public static void main(String[] args) {
            //接收一个泛型参数，比较返回boolean值
//            Predicate<String> predicate = (v1) -> {
//                //此处别的逻辑处理，做简单的语法介绍就不详细写了。
//                return v1.equals("1");
//            };
        Predicate<String> predicate = (v1) -> v1.equals("2"); //直接返回结果可这样写
            System.out.println(predicate.test("3"));//false
            System.out.println(predicate.test("1"));//false
            System.out.println(predicate.test("2"));//true

        }
}
