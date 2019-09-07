package com.zhirui.lmwy.wms.demo.multithread;

import org.apache.commons.collections4.map.HashedMap;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName ReentrantLockTest
 * @Description 证明一个ReentrantLock对象的lock和unlick只针对同一个对象有效
 * @Author longxiaonan@163.com
 * @Date 2018/9/11 0011 19:28
 */
public class ReentrantLockTest {

    static AtomicInteger ato = new AtomicInteger(0);

    static Map<Integer,ReentrantLock> hashMap = new HashedMap();
    public static void main(String[] args) throws Exception {
        generate();
        ExecutorService service = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++){
            service.submit(()->{
                try {
                    int j = ato.getAndIncrement();
                    test(j);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public static void generate() throws Exception {
        for(int i = 0; i < 10; i++){
            hashMap.put(i,new ReentrantLock());
        }
    }

    public static void test(Integer key) throws Exception{
        ReentrantLock reentrantLock = hashMap.get(key);
        System.out.println(key+"执行开始");
        reentrantLock.lock();
        Thread.sleep(2000);
        reentrantLock.unlock();
        System.out.println(key+"执行结束");

    }

}
