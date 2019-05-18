package com.redis.service;

import com.redis.entity.User;
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
    public User findUser(Integer id) {
        User user = new User();
        user.setId(id);
        user.setName("hlhdidi");
        user.setAge(18);
        System.out.println(user);
        return user;
    }

    @CacheEvict(value = "thisredis", key = "'users_'+#id", condition = "#id!=2")
    public void delUser(Integer id) {
        // 删除user
        System.out.println("user删除");
    }
}
