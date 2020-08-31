package com.iee.lambda.bb;

import com.iee.common.entity.User;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * @ClassName StreamCreate
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2020/8/31 0031 13:18
 */
public class StreamCreate {

    //创建Stream
    @Test
    public void test1() {
        //1。可以通过Coliection系列集合提供的stream()或parallelstream()
        List<String> list = new ArrayList<>();
        Stream<String> stream1 = list.stream();

        //2。通过Arrays中的静态方法stream(获取数组流
        User[] emps = new User[10];
        Stream<User> stream2 = Arrays.stream(emps);

        //3。通过Stream类中的静态方法of(
        Stream<String> stream3 = Stream.of("aa", "bb", "cc");

        //4。创建无限流
        //迭代
        Stream<Integer> stream4 = Stream.iterate(0, (x) -> x + 2);
        stream4.limit(10).forEach(System.out::println);
        //生成
        Stream.generate(() -> Math.random())
                .limit(5)
                .forEach(System.out::println);
    }
}
