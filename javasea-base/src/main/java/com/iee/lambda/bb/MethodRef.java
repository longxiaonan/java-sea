package com.iee.lambda.bb;

import com.iee.common.entity.User;
import org.junit.Test;

import java.io.PrintStream;
import java.util.Comparator;
import java.util.function.*;

/**
 * 1方法引用:若Lambda 体中的内容有方法已经实现了，我们可以使用"方法引用"
 * （可以理解为方法引用是Lambda 表达式的另外一种表现形式）
 * 主要有三种语法格式:
 * 对象::实例方法名
 * 类::静态方法名
 * 类::实例方法名
 * 注意:
 * Lambda体中调用方法的参数列表与返回值类型]要与函数式接口中抽象方法的函数列表和返回值类型保持一致!
 *
 * 2构造引用：
 * 格式：
 * ClassName::new
 * 注意:需要调用的构造器的参数列表要与函数式接口中抽象方法的参数列表保持一致!
 *
 * 3数组引用：
 * Type[]::new;
 *
 */
public class MethodRef {

    //数组引用:
    @Test
    public void test7( ) {
        Function<Integer, String[]> fun = (x) -> new String[x];
        String[] strs = fun.apply(10);
        System.out.println(strs.length);
        Function<Integer, String[]> fun2 = String[]::new;
        String[ ] strs2 = fun2.apply(20);
        System . out . println( strs2.length);
    }

    //构造器引用2
    @Test
    public void test6(){
        Function<String,User> fun=(x)->new User(x);
        // 采用无参构造器
        Function<String,User>fun2=User::new;
        // 采用一个参数的构造器
        User emp=fun2.apply("101");
        System.out.println(emp);
        // 采用两个参数的构造器
        BiFunction<String,Integer,User> bf=User::new;
    }

    //构造器引用1
    @Test
    public void test5(){
        Supplier<User> sup = () -> new User();
        //构造器引用方式
        Supplier<User> sup2 = User::new;
        User emp = sup2.get();
        System.out.println(emp);
    }

    //类::实例方法名
    @Test
    public void test4() {
        BiPredicate<String, String> bp = (x, y) -> x.equals(y);
        BiPredicate<String, String> bp2 = String::equals;
    }


    //类::静态方法名
    @Test
    public void test3() {
        Comparator<Integer> com = (x, y) -> Integer.compare(x, y);
        Comparator<Integer> com1 = Integer::compare;
    }

    //对象::实例方法名
    @Test
    public void test1() {
        PrintStream ps1 = System.out;
        Consumer<String> con = (x) -> ps1.println(x);
        PrintStream ps = System.out;
        Consumer<String> con1 = ps::println;
        Consumer<String> con2 = System.out::println;
        con2.accept("abcdef");
    }

    //对象::实例方法名2
    @Test
    public void test2() {
        User emp = new User();
        Supplier<String> sup = () -> emp.getName();
        String str = sup.get();
        System.out.println(str);
        Supplier<Integer> sup2 = emp::getAge;
        Integer integer = sup2.get();
        System.out.println(integer);
    }
}
