package com.iee.thread.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName FutureDemo
 * @Description 参考：https://juejin.im/post/5d35d6ebe51d45109b01b270?utm_source=gold_browser_extension#heading-8
 * @Author longxiaonan@163.com
 * @Date 2019/7/23 0023 15:17
 */
public class a {
    public void test6() throws Exception {
        CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello";
        }).thenAcceptBoth(CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "ijiangtao";
        }), (s1, s2) -> {
            System.out.println(s1 + " " + s2);
        });

        while (true){

        }
    }

}
