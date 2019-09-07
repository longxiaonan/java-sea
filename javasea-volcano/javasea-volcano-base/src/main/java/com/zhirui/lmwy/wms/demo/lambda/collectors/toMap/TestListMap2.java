package com.zhirui.lmwy.wms.demo.lambda.collectors.toMap;

import com.google.common.collect.Lists;
import com.zhirui.lmwy.wms.demo.entity.Student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TestListMap2 {
    public static void main(String[] args) {
//        List<User> list1 = new ArrayList<>();
//        list1.add(new User(1,"longxiaon", 28));
        List<Student> list = new ArrayList<>();
        list.add(new Student(1, "randomdemo2.com", 80000));
        list.add(new Student(3, "163.com", 200));
        list.add(new Student(2, "baidu.com", 120000));
        list.add(new Student(5, "taobao.com", 200000));
//        list.add(new User(4, "uuu9.com", 1));

        list.add(new Student(6, "randomdemo2.com", 80000));
        list.add(new Student(7, "randomdemo2.com", 80000));
        list.add(new Student(8, "163.com", 100));

        Map<String, Map<Integer,Integer>> result =
                list.stream().collect(
                        Collectors.groupingBy(
                                Student::getName, Collectors.toMap(Student::getId, Student::getAge)
                        )
                );
        System.out.println(result);

        Map<String, List<Student>> result2 =
                list.stream().collect(
                        Collectors.groupingBy(x -> x.getName())
                );
        System.out.println("result2>>"+result2);

//        list = Lists.<User>newArrayList();
        int size = list.stream().map(a -> a.getName()).collect(Collectors.toSet()).size();
        System.out.println("size:"+size);
        HashMap<String, List<Student>> collect = list.stream()
//                .collect(() -> new HashMap<String, List<User>>(),
                  .collect(HashMap::new,
                        (h, x) -> {
                            List<Student> value = h.getOrDefault(x.getName(), Lists.newArrayList());
                            value.add(x);
                            h.put(x.getName(), value);
                        },
                        HashMap::putAll
                );
        System.out.println("collect:"+collect);//结果和resul2一样
        System.out.println("collect.Size:"+collect.size());

        Integer collect1 = collect.values().stream().map(a -> a.stream().mapToInt(Student::getAge).sum())
                .min(Integer::compareTo).orElseThrow(() -> new RuntimeException(""));

        System.out.println("collect111111>>"+collect1);

        collect.forEach((k,v) -> {
            int sum = v.stream().mapToInt(a -> a.getAge()).sum();
            System.out.println("sum>>" + k +":::"+ sum);
        });

        // key = id, value - websites
        Map<Integer, Integer> result1 = list.stream().collect(
                Collectors.toMap(Student::getId, Student::getAge));
        System.out.println("Result 1 : " + result1);
    }
}
