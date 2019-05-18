package com.redis.controller;

import com.redis.entity.User;
import com.redis.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/** 单机和集群模式都能通过StringRedisTemplate和RedisTemplate操作
 * redisTemplate.opsForValue();//操作字符串
 * redisTemplate.opsForHash();//操作hash
 * redisTemplate.opsForList();//操作list
 * redisTemplate.opsForSet();//操作set
 * redisTemplate.opsForZSet();//操作有序set
 * */
@RestController
public class TestRedisController {

	@Autowired
	private RedisTemplate<String,Object> redisTemplate;

	@Autowired
	private RedisUtil redisUtil;

	@GetMapping("/testStringRedisTemplate")
	public void testSet(){
		System.out.println(">>>web111111");
		SetOperations<String, Object> stringStringSetOperations = redisTemplate.opsForSet();
        ValueOperations<String, Object> stringStringValueOperations = redisTemplate.opsForValue();

        stringStringSetOperations.add("aa","bb");
        Set<Object> aa = stringStringSetOperations.members("aa");
        System.out.println(aa);
	}

	/** 能直接操作对象，但是在客户端查看不到值 */
	@GetMapping("/testRedisTemplate")
	public void testAA(){
		User user = new User();
		user.setName("longxn");
		user.setAge(88);
		redisTemplate.opsForValue().set("a221",user);
		System.out.println("user>>>"+user);
        User user1  = (User)redisTemplate.opsForValue().get("a221");
        System.err.println("user1>>>"+user1);
    }

    /** 测试通过redisUtil来实现 */
    @GetMapping("/testRedisUtil")
	public void testRedisUtil(){
		redisUtil.set("aaa","a1111");
		User user = new User();
		user.setName("longxn");
		user.setAge(88);
		redisTemplate.opsForValue().set("a221",user);
		System.out.println(redisUtil.hset("bbb","b111",user));
		String aa = (String)redisUtil.get("aaa");
		System.out.println(redisUtil.hget("bbb","b111"));
	}
}
