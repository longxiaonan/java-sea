package com.iee.common.lambda;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @ClassName mapTest
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2018/11/4 0004 18:14
 */
public class mapTest {
    public static void main(String[] args) {

//        flatMap();

//        filter();

//        sort();

    }

    private static void sort() {

        //java7
        List<String> nameList = Arrays.asList("Ted", "Bobo", "Alice");
        nameList.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });

        //java8
        List<String> nameList2 = Arrays.asList("Ted", "Bobo", "Alice");
        nameList2.sort((String o1,String o2)->o1.compareTo(o2));
        System.out.println(nameList);
    }

    private static void filter() {
        Integer[] nums={0,1,2,3,4,5,6,7,8,9};
        List<Integer> oddNums= Stream.of(nums).filter(n->(n&1)==1).collect(Collectors.toList());
        System.out.println(oddNums);
    }

    private static void flatMap() {
        List<String> hiList = Arrays.asList("hello", "hi", "你好");
        List<String> nameList = Arrays.asList("Ted", "Bobo", "Alice");

        hiList.stream().flatMap(hi -> nameList.stream().

                map(name -> hi + " " + name)).

                collect(Collectors.toList()).

                forEach(System.out::println);
    }
}
