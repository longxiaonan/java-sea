package com.zhirui.lmwy.wms.demo.web.controller;

import com.zhirui.lmwy.common.redis.RedisUtils;
import com.zhirui.lmwy.wms.demo.web.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName TestRedisController
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2019/9/5 0005 17:43
 */
@RestController
public class TestRedisController {

    @Autowired
    private RedisUtils redisUtils;

    @GetMapping(value = "testRedis")
    public void redisTester() {
        int i = 0;
        long start = System.currentTimeMillis();// 开始毫秒数
        while (true) {
            long end = System.currentTimeMillis();
            if (end - start >= 1000) {// 当大于等于1000毫秒（相当于1秒）时，结束操作
                break;
            }
            i++;
            redisUtils.set("xiao" + i, User.builder().username("longxiaonan"+i).age(18).build());
            redisUtils.lSet("list", User.builder().username("longxiaonan"+i).age(18).build());
            redisUtils.hset("banks:12600000", "a"+i, "b"+i);
        }
        // 打印1秒内对Redis的操作次数
        System.out.println("redis每秒操作：" + i + "次");
    }

}
