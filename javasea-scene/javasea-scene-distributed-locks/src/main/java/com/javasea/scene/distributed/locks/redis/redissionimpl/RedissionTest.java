package com.javasea.scene.distributed.locks.redis.redissionimpl;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName redissionimpl
 * @Description
 * 1. 通过redissonClient获取RLock实例
 * 2. tryLock获取尝试获取锁，第一个是等待时间，第二个是锁的超时时间，第三个是时间单位
 * 3. 执行完业务逻辑后，最终释放锁
 * @Author longxiaonan@163.com
 * @Date 2019/10/13 0013 22:21
 */
@RestController
public class RedissionTest {

    @Autowired
    private static RedissonClient redissonClient;

    @GetMapping("/test/redission")
    public void test(){
        RLock distribute_lock = redissonClient.getLock("distribute_lock");
        try {
            boolean result = distribute_lock.tryLock(3, 10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (distribute_lock.isLocked()) {
                distribute_lock.unlock();
            }
        }
    }
}
