package com.javasea.redis.service;

import com.javasea.redis.entity.Person;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @ClassName TestCacheService
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2018/9/13 0013 13:16
 */
@Service
public class TestCacheService {

    @Cacheable(value = "thisredis", key = "'users_'+#id")
    public Person findUser(Integer id) {
        Person p = new Person();
        p.setId("11");
        p.setName("longxiaonan");
        return p;
    }

    @CacheEvict(value = "thisredis", key = "'users_'+#id", condition = "#id!=2")
    public void delUser(Integer id) {
        // 删除user
        System.out.println("user删除");
    }
}
