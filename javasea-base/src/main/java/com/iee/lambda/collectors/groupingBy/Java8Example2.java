package com.iee.lambda.collectors.groupingBy;



import com.iee.common.entity.Student;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Java8Example2 {
    public static void main(String[] args) {
//        test1();
        test2();
    }

    private static void test2() {
        List<Student> students = Arrays.asList(new Student(1,"张三", 19),
                new Student(2,"张三", 58),
                new Student(3,"李四", 38),
                new Student(4,"赵五", 48)
        );
        Map<String, List<Student>> collect3 = students.stream().collect(Collectors.groupingBy(x -> x.getName()));
        System.out.println(collect3);
    }

    private static void test1() {
        //3 apple, 2 banana, others 1
        List<String> items =
                Arrays.asList("apple", "apple", "banana",
                        "apple", "orange", "banana", "papaya");
        Map<String, Long> result =
                items.stream().collect(
                        Collectors.groupingBy(
                                Function.identity(), Collectors.counting()
                        )
                );
        System.out.println(result);
        Map<String, Long> finalMap = new LinkedHashMap<>();
        //Sort a map and add to finalMap
        result.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue()
                        .reversed()).forEachOrdered(e -> finalMap.put(e.getKey(), e.getValue()));
        System.out.println(finalMap);
    }
}
