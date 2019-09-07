package com.iee.redis.distributedlocks;
import redis.clients.jedis.Jedis;

import java.util.Collections;
import java.util.List;

/**
 * @desc redis队列实现方式
 * @anthor
 * @date
 **/
public class RedisUcUitl {

    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";

    private static final Long RELEASE_SUCCESS = 1L;

    private RedisUcUitl() {

    }
    /**
     * logger
     **/

    /**
     * 存储redis队列顺序存储 在队列首部存入
     *
     * @param key   字节类型
     * @param value 字节类型
     */
    public static Long lpush(Jedis jedis, final byte[] key, final byte[] value) {

        return jedis.lpush(key, value);

    }

    /**
     * 移除列表中最后一个元素 并将改元素添加入另一个列表中 ，当列表为空时 将阻塞连接 直到等待超时
     * 原子性地返回并删除存储在source列表的最后一个元素(尾)，并将该元素推入并存储为destination列表的第一个元素(头)。
     * 如果source不存在，超时为0可用于无限期地阻塞。
     * 超时返回NULL。如果source和destination相同，则操作等同于从列表中移除最后一个元素，
     * 并将其作为列表的第一个元素推入，因此可以将其视为列表回转命令（rotation command）。
     *
     * @param srckey
     * @param dstkey
     * @param timeout 0 表示永不超时
     * @return
     */
    public static byte[] brpoplpush(Jedis jedis,final byte[] srckey, final byte[] dstkey, final int timeout) {

        return jedis.brpoplpush(srckey, dstkey, timeout);

    }

    /**
     * 返回制定的key,起始位置的redis数据
     * @param redisKey
     * @param start
     * @param end -1 表示到最后
     * @return
     */
    public static List<byte[]> lrange(Jedis jedis,final byte[] redisKey, final long start, final long end) {

        return jedis.lrange(redisKey, start, end);
    }

    /**
     * 删除key
     * @param redisKey
     */
    public static void delete(Jedis jedis, final byte[] redisKey) {
        jedis.del(redisKey);
    }

    /**
     * 尝试加锁
     * @param lockKey key名称
     * @param requestId 身份标识
     * @param expireTime 过期时间
     * @return
     */
    public static boolean tryGetDistributedLock(Jedis jedis,final String lockKey, final String requestId, final int expireTime) {
        String result =  jedis.set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
        return LOCK_SUCCESS.equals(result);

    }

    /**
     * 释放锁
     * @param lockKey key名称
     * @param requestId 身份标识
     * @return
     */
    public static boolean releaseDistributedLock(Jedis jedis,final String lockKey, final String requestId) {
        final String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));
        return RELEASE_SUCCESS.equals(result);

    }
}

