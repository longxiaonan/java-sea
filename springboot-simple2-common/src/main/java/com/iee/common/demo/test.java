package com.iee.common.demo;

import com.google.common.collect.Lists;
import com.iee.common.entity.User;
import org.apache.commons.collections.MapUtils;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @ClassName test
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2018/11/6 0006 12:01
 */
public class test {
    public static void main(String[] args) {
        //通过工艺id查询工序list
        List<User> user = Lists.newArrayList();
        User user1 = new User();
        user1.setNumber((short)2);
        User user2 = new User();
        user2.setNumber((short)3);
        User user3 = new User();
        user3.setNumber((short)1);
        User user4 = new User();
        user4.setNumber((short)4);
        user.add(user1);user.add(user2);user.add(user3);user.add(user4);
        System.out.println(user);
        //通过roderNumber排序, 排序后的工序才是工艺路线, 从小到大排序
        List<User> aa = user.stream().sorted(Comparator.comparing(User::getNumber).reversed()).collect(Collectors.toList());

        int minFinalCount = user.stream().mapToInt(User::getNumber).min().getAsInt();

        System.out.println(aa);

        Long[] bb = {null, null};

    }
}
