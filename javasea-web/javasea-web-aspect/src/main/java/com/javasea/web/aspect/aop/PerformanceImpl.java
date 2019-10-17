package com.javasea.web.aspect.aop;

import org.springframework.stereotype.Component;

@Component
public class PerformanceImpl implements Performance {
    @Override
    public void perform() {
        System.out.println("the perform is good");
//        throw new RuntimeException("ssss");
    }

    @Override
    public void many() {
        System.out.println("the many is good");
    }

    @Override
    public void perform2(String name) {
        System.out.println("下面请 " + name + " 开始他的表演");
    }
}
