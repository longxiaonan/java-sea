package com.zhirui.lmwy.wms.demo.web.controller;

import com.google.common.collect.Lists;
import com.zhirui.lmwy.wms.demo.web.entity.Student;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName TestSpringCache
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2019/9/11 0011 16:48
 */
@RestController
@CacheConfig(cacheNames = "test")  //使用cacheManager定义的cacheName
public class TestSpringCacheService {

    private String getTenantId(){
        return "tenantId1234";
    }

    //需要实现 Serializable 接口
    @Cacheable(value = "thisredis11", key = "'users_'+#id")
    public Student findStudent11(Integer id) {
        Student p = new Student();
        p.setId(id);
        p.setName("longxiaonan");
        p.setBirth(new Date());
        return p;
    }

    @Cacheable(value = "findStudent11List", key = "'users_'+#id")
    public List<Student> findStudent11List(Integer id) {
        Student p = new Student();
        p.setId(id);
        p.setName("longxiaonan");
        p.setBirth(new Date());
        ArrayList<Student> list = Lists.newArrayList();
        list.add(p);
        return list;
    }

    @CacheEvict(value = "thisredis11", key = "'users_'+#id")
    public void delStudent12(Integer id) {
        // 删除user
        System.out.println("delStudent12");
    }

//    @Cacheable(value = "thisredis21", key = "#root.target.getTenantId()+#id")
    @Cacheable(value = "thisredis21", key = "#root.target.getTenantId()+'aa'")
    public Student findStudent21(Integer id) {
        Student p = new Student();
        p.setId(id);
        p.setName("zhangsan");
        p.setBirth(new Date());
        return p;
    }

    @Cacheable(value = "testcachename", key = "'cachennnn'+'aa'")
    public Student testcachename(Integer id) {
        Student p = new Student();
        p.setId(id);
        p.setName("zhangsan");
        p.setBirth(new Date());
        return p;
    }

    @CacheEvict(value = "thisredis22", key = "#root.target.getTenantId()+#id", condition = "#id!=2")
    public void delStudent22(Integer id) {
        // 删除user
        System.out.println("delStudent22");
    }

}
