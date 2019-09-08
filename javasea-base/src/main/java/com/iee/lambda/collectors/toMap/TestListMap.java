package com.iee.lambda.collectors.toMap;


import com.iee.entity.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TestListMap {
    public static void main(String[] args) {
//        List<User> list1 = new ArrayList<>();
//        list1.add(new User(1,"longxiaon", 28));
        List<Student> list = new ArrayList<>();
        list.add(new Student(1, "randomdemo2.com", 80000));
        list.add(new Student(3, "163.com", 90000));
        list.add(new Student(2, "baidu.com", 120000));
        list.add(new Student(5, "taobao.com", 200000));
        list.add(new Student(4, "uuu9.com", 1));


        // key = id, value - websites
        Map<Integer, Integer> result1 = list.stream().collect(
                Collectors.toMap(Student::getId, Student::getAge));
        System.out.println("Result 1 : " + result1);
        // key = name, value - websites
        Map<String, Integer> result2 = list.stream().collect(
                Collectors.toMap(Student::getName, Student::getAge));
        System.out.println("Result 2 : " + result2);
        // Same with result1, just different syntax
        // key = id, value = name
        Map<Integer, String> result3 = list.stream().collect(
                Collectors.toMap(x -> x.getId(), x -> x.getName()));
        System.out.println("Result 3 : " + result3);




    }
}
