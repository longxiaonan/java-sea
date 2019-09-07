package com.javasea.web.aspect.aop;

/**
 * @ClassName Performance
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2019/7/25 0025 19:57
 */
public interface Performance {
    public void perform();

    public void many();

    void perform2(String name);
}
