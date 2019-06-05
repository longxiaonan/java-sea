package com.iee.ehcache.redis.config;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.stereotype.Component;

import javax.cache.CacheManager;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.Duration;
import javax.cache.expiry.TouchedExpiryPolicy;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * 实现 JCacheManagerCustomizer.customize(CacheManager cacheManager) 方法
 * 在 CacheManager 使用之前，创建我们配置文件定义的缓存,并声明了缓存策略为10秒。
 */
@Component
public  class CachingSetup implements JCacheManagerCustomizer {
    @Override
    public void customize(CacheManager cacheManager)
    {
      cacheManager.createCache("people", new MutableConfiguration<>()
        .setExpiryPolicyFactory(TouchedExpiryPolicy.factoryOf(new Duration(SECONDS, 10)))
        .setStoreByValue(false)
        .setStatisticsEnabled(true));
    }
}
