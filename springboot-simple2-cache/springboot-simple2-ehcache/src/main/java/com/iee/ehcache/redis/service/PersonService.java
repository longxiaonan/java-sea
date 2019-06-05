package com.iee.ehcache.redis.service;

import com.iee.ehcache.redis.entity.Person;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    private Person p1 = new Person(1, "Steve", "jobs");
    private Person p2 = new Person(2, "bill", "gates");
    private Person p3 = new Person(3, "unknown", "unknown");
    //* @Cacheable : Spring在每次执行前都会检查Cache中是否存在相同key的缓存元素，如果存在就不再执行该方法，而是直接从缓存中获取结果进行返回，否则才会执行并将返回结果存入指定的缓存中。
    //* @CacheEvict : 清除缓存。用在update，delete方法上。update也可以用@CachePut
    //* @CachePut : @CachePut也可以声明一个方法支持缓存功能。使用@CachePut标注的方法在执行前不会去检查缓存中是否存在之前执行过的结果，而是每次都会执行该方法，并将执行结果以键值对的形式存入指定的缓存中。

    //@CacheResult 必须指定 cacheName，否则 cacheName 默认视为该方法名称。
//    @CacheResult(cacheName = "people")
    @Cacheable(cacheNames = "people")
//    @Cacheable(value = "tttt", key = "'users_'+#id") #redis作为缓存时候的写法
    public Person getPerson(int id) {
        System.out.println("未从缓存读取 " + id);
        switch (id) {
            case 1:
                return p1;
            case 2:
                return p2;
            default:
                return p3;
        }
    }

    @CacheEvict(cacheNames = "people")
    public void updatePerson(int id) {
        System.out.println("更新缓存 " + id);
    }

    @CacheEvict(cacheNames = "people")
    public void delete(int id) {
        System.out.println("删除缓存 " + id);
    }

}
