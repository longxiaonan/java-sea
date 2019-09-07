package com.iee.redis.subscr.publish;

import com.iee.redis.subscr.utils.SpringContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jca.context.SpringContextResourceAdapter;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class PubSubManager extends Thread{
    private static Logger logger = LoggerFactory.getLogger(PubSubManager.class);

    public static Jedis jedis;
    RedisMsgPubSub msgPubSub = new RedisMsgPubSub();
    //频道
    public static final String PUNSUB_CONFIG = "pubsub_config";
    //1.将所有需要刷新加载的Service类（实现ICacheUpdate接口）添加到RedisMsgPubSub的updates中
    public boolean addListener(String key, ICacheUpdate listener){
        return msgPubSub.addListener(key,listener);
    }
    @Override
    public void run(){
        while (true){
            try {
                JedisPool jedisPool = SpringContextUtil.getBean(JedisPool.class);
                if(jedisPool!=null){
                    jedis = jedisPool.getResource();
                    if(jedis!=null){
                        //2.启动线程订阅pubsub_config频道 阻塞
                        jedis.subscribe(msgPubSub,PUNSUB_CONFIG);
                    }
                }
            } catch (Exception e) {
                logger.error("redis connect error!");
            } finally {
                if(jedis!=null)
                    jedis.close();
            }
            try {
                //3.收到消息后的五秒后再次订阅（避免订阅到一次消息后结束订阅）
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                logger.error("InterruptedException in redis sleep!");
            }
        }
    }
}
