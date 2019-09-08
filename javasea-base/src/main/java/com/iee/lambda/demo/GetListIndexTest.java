package com.iee.lambda.demo;

import com.google.common.collect.Lists;
import com.iee.entity.Student;

import java.util.ArrayList;
import java.util.Optional;

/**
 * @ClassName SortTest
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2018/11/2 0002 22:36
 */
public class GetListIndexTest {
    public static void main(String[] args) {
        Student user  = new Student();
        user.setName("long");
        Student user2  = new Student();
        user2.setName("xiao");
        Student user3  = new Student();
        user3.setName("nan");
        ArrayList<Student> students = Lists.newArrayList();
        students.add(user3);
        students.add(user2);
        students.add(user);
        String a = "long";
        System.out.println(students);
        //java7 求指定name的user的索引
        students.forEach(b ->{
            if(b.getName().equals(a)){
                System.out.println(b);
                int i = students.indexOf(b);
                System.out.println(i);
            }
        });
        //java8 求指定name的user的索引
        Integer integer = students.stream().filter(b -> b.getName().equals(a)).map(c -> students.indexOf(c)).findFirst().get();
        System.out.println(integer);
        if(integer < students.size() - 1) {
            students.get(integer + 1);
        }else{
            System.out.println("已经是最后一个用户");
        }


        Student u1 = null;
        Optional<Student> u11 = Optional.ofNullable(u1);
        if(u11.isPresent()){
            System.out.println(u11);
        }
        Student user1 = u11.get();
    }

}
