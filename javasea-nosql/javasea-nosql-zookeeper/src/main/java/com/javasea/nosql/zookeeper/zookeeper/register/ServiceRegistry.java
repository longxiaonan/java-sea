package com.javasea.nosql.zookeeper.zookeeper.register;

import java.util.ArrayList;

public interface ServiceRegistry {
    /**
     * 注册服务信息
     *
     * @param serviceName    服务名称
     * @param serviceAddress 服务地址
     */
    void register(String serviceName, String serviceAddress);
    ArrayList<String> getValue(String path);
}
