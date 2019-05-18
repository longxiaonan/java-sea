package com.iee.common.lambda;

import com.google.common.collect.Lists;
import com.iee.common.entity.User;
import com.iee.common.utils.LocalDateTimeUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @ClassName test
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2018/11/2 0002 22:36
 */
public class test {
    public static void main(String[] args) {
        User user  = new User();
        user.setName("long");
        User user2  = new User();
        user2.setName("xiao");
        User user3  = new User();
        user3.setName("nan");
        ArrayList<User> objects = Lists.newArrayList();
        objects.add(user3);
        objects.add(user2);
        objects.add(user);
        String a = "long";
        System.out.println(objects);
        //java7 求指定name的user的索引
        objects.forEach(b ->{
            if(b.getName().equals(a)){
                System.out.println(b);
                int i = objects.indexOf(b);
                System.out.println(i);
            }
        });
        //java8 求指定name的user的索引
        Integer integer = objects.stream().filter(b -> b.getName().equals(a)).map(c -> objects.indexOf(c)).findFirst().get();
        System.out.println(integer);
        if(integer < objects.size() - 1) {
            objects.get(integer + 1);
        }else{
            System.out.println("已经是最后一个用户");
        }
        
        
        User u1 = null;
        Optional<User> u11 = Optional.ofNullable(u1);
        if(u11.isPresent()){
            System.out.println(u11);
        }
        User user1 = u11.get();
    }
    
}
