package com.zhirui.lmwy.wms.demo.lambda;

import com.google.common.collect.Lists;
import com.zhirui.lmwy.wms.demo.entity.Student;

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
        List<Student> users = Lists.newArrayList();
        Student u1 = new Student();
        u1.setName("long");
        u1.setAge(18);
        Student u2 = new Student();
        u2.setName("xiao");
        u2.setAge(19);
        Student u3 = new Student();
        u3.setName("nan");
        u3.setAge(20);
        users.add(u1);
        users.add(u2);

        //只有当filter条件满足的时候, 获取第一个数据，否则抛出异常
        Student user = Optional.of(users).filter(a -> a.size() == 1).orElseThrow(() -> new RuntimeException("查询的数据异常")).get(0);
        System.out.println(user);
    }
}
