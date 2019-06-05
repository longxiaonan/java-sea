package com.iee.ehcache.redis.logger;

import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 日志记录器 */
public class EventLogger implements CacheEventListener<Object, Object> {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventLogger.class);

    @Override
    public void onEvent(CacheEvent<?, ?> event) {
        LOGGER.info("Event: " + event.getType() + " Key: " + event.getKey() + " old value: " + event.getOldValue() + " new value: " + event.getNewValue());
    }

}
