package com.iee.lambda;

import com.google.common.collect.Lists;
import com.iee.common.entity.Student;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName SortTest
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2018/11/6 0006 12:01
 */
public class SortTest {
    public static void main(String[] args) {
        //通过工艺id查询工序list
        List<Student> user = Lists.newArrayList();
        Student user1 = new Student();
        user1.setNumber((short)2);
        Student user2 = new Student();
        user2.setNumber((short)3);
        Student user3 = new Student();
        user3.setNumber((short)1);
        Student user4 = new Student();
        user4.setNumber((short)4);
        user.add(user1);user.add(user2);user.add(user3);user.add(user4);
        System.out.println(user);
        //通过roderNumber排序, 排序后的工序才是工艺路线, 从小到大排序
        List<Student> aa = user.stream().sorted(Comparator.comparing(Student::getNumber).reversed()).collect(Collectors.toList());

        int minFinalCount = user.stream().mapToInt(Student::getNumber).min().getAsInt();

        System.out.println(aa);

        Long[] bb = {null, null};

    }

    private static void sort() {

        //java7
        List<String> nameList = Arrays.asList( "Bobo", "Ted", "Alice");
        nameList.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                System.out.println(o1 + "," + o2 + ":" + o1.compareTo(o2));
                return o1.compareTo(o2);
            }
        });

        //java8
        List<String> nameList2 = Arrays.asList("Ted", "Bobo", "Alice");
        nameList2.sort((String o1,String o2)->o1.compareTo(o2));
        System.out.println(nameList);
    }
}
