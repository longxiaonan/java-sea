package com.iee.redis.subscr.publish;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisPubSub;

import java.util.HashMap;
import java.util.Map;

/**
 * Redis发布订阅的扩展类
 * 作用：1、统一管理ICacheUpdate，把所有实现ICacheUpdate接口的类添加到updates容器
 * 2、重写onMessage方法，订阅到消息后进行刷新缓存的操作
 */
public class RedisMsgPubSub extends JedisPubSub {
    private static Logger logger = LoggerFactory.getLogger(RedisMsgPubSub.class);
    private Map<String , ICacheUpdate> updates = new HashMap<>();


    //1、由updates统一管理ICacheUpdate
    public boolean addListener(String key , ICacheUpdate update) {
        if(update == null)
            return false;
	updates.put(key, update);
	return true;
    }
    /**
     * 2、重写onMessage方法，订阅到消息后进行刷新缓存的操作
     * 订阅频道收到的消息
     */
    @Override
    public void onMessage(String channel, String message) {
        logger.info("RedisMsgPubSub onMessage channel:{},message :{}" ,channel, message);
        ICacheUpdate updater = null;
        if(StringUtils.isNotEmpty(message))
            updater = updates.get(message);
        if(updater!=null)
            updater.update();
    }
    //other code...
}
