package com.javasea.easyexcel.relase.executor;

import java.util.List;

/**
 * @ClassName HandeExecutor
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2020/3/25 0025 11:54
 */
@FunctionalInterface
public interface HandeExecutor<T> {

    void exec(List<T> t);

}
