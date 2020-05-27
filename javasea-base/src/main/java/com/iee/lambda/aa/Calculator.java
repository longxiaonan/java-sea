package com.iee.lambda.aa;

/**
 * 该方法operateBinary对两个整数进行数学运算。由IntegerMath的具体实现来计算。
 * 示例中定义了两个Lambda表达式：addition 和 subtraction。示例输出以下内容：
 * 40 + 2 = 42
 * 20-10 = 10
 */
public class Calculator {

    interface IntegerMath {
        int operation(int a, int b);
    }

    public int operateBinary(int a, int b, IntegerMath op) {
        return op.operation(a, b);
    }

    public static void main(String... args) {

        Calculator myApp = new Calculator();
        IntegerMath addition = (a, b) -> a + b;
        IntegerMath subtraction = (a, b) -> a - b;
        System.out.println("40 + 2 = " + myApp.operateBinary(40, 2, addition));
        System.out.println("20 - 10 = " + myApp.operateBinary(20, 10, subtraction));
    }
}
