package com.javasea.redis.config;

import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;
import java.util.EnumSet;
import java.util.stream.Collectors;

/**
 * redis 缓存配置.
 *
 * @author felord.cn
 * @since 2020 /8/17 20:14
 */
@EnableCaching
@Configuration
public class CacheConfiguration {


    /**
     * Redis cache configuration.
     *
     * @param redisTemplate the redis template
     * @return the redis cache configuration
     */
    @Bean
    public RedisCacheConfiguration redisCacheConfiguration(RedisTemplate<Object, Object> redisTemplate, CacheProperties cacheProperties) {
         // 参见 spring.cache.redis
        CacheProperties.Redis redisProperties = cacheProperties.getRedis();

        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                // 缓存的序列化问题
                .serializeValuesWith(RedisSerializationContext.SerializationPair
                        .fromSerializer(redisTemplate.getValueSerializer()));

        if (redisProperties.getTimeToLive() != null) {
            // 全局 TTL 时间
            redisCacheConfiguration = redisCacheConfiguration.entryTtl(redisProperties.getTimeToLive());
        }
        if (redisProperties.getKeyPrefix() != null) {
            // key 前缀值
            redisCacheConfiguration = redisCacheConfiguration.prefixCacheNameWith(redisProperties.getKeyPrefix());
        }
        if (!redisProperties.isCacheNullValues()) {
            // 默认缓存null值 可以防止缓存穿透
            redisCacheConfiguration = redisCacheConfiguration.disableCachingNullValues();
        }
        if (!redisProperties.isUseKeyPrefix()) {
            // 不使用key前缀
            redisCacheConfiguration = redisCacheConfiguration.disableKeyPrefix();
        }
        return redisCacheConfiguration;
    }


    /**
     * Redis cache manager 个性化配置缓存过期时间.
     * @see RedisCacheManager,CacheEnum
     * @return the redis cache manager builder customizer
     */
    @Bean
    public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer(RedisCacheConfiguration redisCacheConfiguration) {

        return builder -> builder.cacheDefaults(redisCacheConfiguration)
                // 自定义的一些缓存配置初始化 主要是特定缓存及其ttl时间
                .withInitialCacheConfigurations(EnumSet.allOf(CacheEnum.class).stream()
                        .collect(Collectors.toMap(CacheEnum::cacheName,
                                cacheEnum -> redisCacheConfiguration.entryTtl(Duration.ofSeconds(cacheEnum.ttlSecond())))));
    }

}
