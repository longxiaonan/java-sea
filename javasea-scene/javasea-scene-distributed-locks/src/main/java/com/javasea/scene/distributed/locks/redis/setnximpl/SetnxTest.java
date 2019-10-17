package com.javasea.scene.distributed.locks.redis.setnximpl;

import redis.clients.jedis.Jedis;

/**
 * @ClassName SetnxTest
 * @Description 通过setnx实现分布式锁
 * 但是可能会导致死锁，且不灵活，不推荐使用该方式
 * @Author longxiaonan@163.com
 * @Date 2019/10/13 0013 22:08
 */
public class SetnxTest {
    public boolean lock(Jedis jedis, String lockName, Integer expire) {

        //返回是否设置成功
        //setNx加锁
        long now = System.currentTimeMillis();
        boolean result = jedis.setnx(lockName, String.valueOf(now + expire * 1000)) == 1;

        if (!result) {
            //防止死锁的容错
            String timestamp = jedis.get(lockName);
            if (timestamp != null && Long.parseLong(timestamp) < now) {
                //不通过del方法来删除锁。而是通过同步的getSet
                String oldValue = jedis.getSet(lockName, String.valueOf(now + expire));
                if (oldValue != null && oldValue.equals(timestamp)) {
                    result = true;
                    jedis.expire(lockName, expire);
                }
            }
        }
        if (result) {
            jedis.expire(lockName, expire);
        }
        return result;
    }
}
