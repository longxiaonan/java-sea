package com.iee.common.lambda;

import com.google.common.collect.Lists;
import com.iee.common.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * @ClassName OptionalTest
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2018/12/11 0011 15:18
 */
public class OptionalTest {
    public static void main(String[] args) {
        List<User> users = Lists.newArrayList();
        User u1 = new User();
        u1.setName("long");
        u1.setAge(18);
        User u2 = new User();
        u2.setName("xiao");
        u2.setAge(19);
        User u3 = new User();
        u3.setName("nan");
        u3.setAge(20);
        users.add(u1);
        users.add(u2);

        //只有当filter条件满足的时候, 获取第一个数据
        User user = Optional.of(users).filter(a -> a.size() == 1).orElseThrow(() -> new RuntimeException("查询的数据异常")).get(0);
        System.out.println(user);
    }
}
