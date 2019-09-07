package com.iee.thread.completablefuture;

import java.util.concurrent.CompletableFuture;

/**
 * @ClassName CompletableFutureExceptionDemo
 * @Description join和get方法都可以阻塞到计算完成然后获得返回结果，但两者的处理流程有所不同
 * join()会在线程里面抛出
 * get()会在get()的时候抛出
 * @Author longxiaonan@163.com
 * @Date 2019/7/23 0023 9:37
 */
public class CompletableFutureExceptionDemo {

    public static void main(String[] args) {
        try {
            new CompletableFutureExceptionDemo().test2();
            new CompletableFutureExceptionDemo().test3();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void test2() throws Exception {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            int i = 1 / 0;
            return 100;
        });
        future.join();
    }

    public void test3() throws Exception {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            int i = 1 / 0;
            return 100;
        });
        future.get();
    }

}
