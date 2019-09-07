package com.iee.cache.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching//启用pring缓存
@SpringBootApplication
public class CacheRedisApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(CacheRedisApplication.class, args);
    }
}
