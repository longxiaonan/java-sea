package com.javasea.scene.distributed.locks.redis.manualimpl;

import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

import java.util.Map;
import java.util.UUID;

/**
 * @ClassName BusinessService
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2019/6/18 0018 21:31
 */
public class BusinessService {

    @Autowired
    Jedis jedis;

    public void test(String param) {

        // 1.先消耗队列中的
        while (true) {
            String keyStr = UUID.randomUUID().toString();
            String dstKeyStr = UUID.randomUUID().toString();
            // 消费队列
            try {
                // 被放入redis队列的数据 序列化后的
                byte[] bytes = RedisUcUitl.brpoplpush(jedis,keyStr.getBytes("UTF-8"), dstKeyStr.getBytes("UTF-8"), 1);
                if (bytes == null || bytes.length <= 0) {
                    // 队列中没数据时退出
                    break;
                }
                // 反序列化对象
                Map<String, Object> singleMap = (Map<String, Object>) ObjectSerialUtil.bytesToObject(bytes);
                // 塞入唯一的值 防止被其他线程误解锁
                String requestId = UUID.randomUUID().toString();
                boolean lockGetFlag = RedisUcUitl.tryGetDistributedLock(jedis,keyStr, requestId, 100);
                if (lockGetFlag) {
                    // 成功获取锁 进行业务处理
                    //TODO
                    // 处理完毕释放锁
                    boolean freeLock = RedisUcUitl.releaseDistributedLock(jedis,keyStr, requestId);

                } else {
                    // 未能获得锁放入等待队列
                    RedisUcUitl.lpush(jedis, keyStr.getBytes("UTF-8"), ObjectSerialUtil.objectToBytes(param));
                }

            } catch (Exception e) {
                break;
            }

        }

// 2.处理最新接到的数据
// 同样是走尝试获取锁，获取不到放入队列的流程
    }

}
