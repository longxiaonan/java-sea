package com.zhirui.lmwy.wms.demo.web.controller;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @ClassName TestLambda
 * @Description 参考：https://blog.csdn.net/qq_39096058/article/details/82864851
 * @Author longxiaonan@163.com
 * @Date 2019/9/20 0020 15:09
 */
public class TestLambda {
    public static void main(String[] args) {
        Object test = testSupplier(RuntimeException::new);
        System.out.println(test);
        Function<Integer, Integer> times2 = i -> i*2;
        Function<Integer, Integer> squared = i -> i*i;

        System.out.println(times2.apply(4));
        //相当于
        System.out.println(new Function<Integer,Integer>() {
            @Override
            public Integer apply(Integer i) {
                return i*2;
            }
        }.apply(4));

        //32                先4×4然后16×2,先执行apply(4)，在times2的apply(16),先执行参数，再执行调用者。
        System.out.println(times2.compose(squared).apply(4));

        //64               先4×2,然后8×8,先执行times2的函数，在执行squared的函数。
        System.out.println(times2.andThen(squared).apply(4));

        //16
        System.out.println(Function.identity().compose(squared).apply(4));


        System.out.println(testFunction(squared,   4));
    }

    private static Object apply(Object o) {
        return null;
    }

    /** Supplier test */
    private static Object testSupplier(Supplier other) {
        return other.get();
    }

    private static Object testFunction(Function function, Integer i){
        return function.apply(i);
    }

    private static Integer apply(Integer i) {
        return i * i;
    }
}
