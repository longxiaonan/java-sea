package com.zhirui.lmwy.wms.demo.lambda;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @ClassName MapTest
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2018/11/4 0004 18:14
 */
public class MapTest {
    public static void main(String[] args) {

//        flatMap();

//        filter();

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
