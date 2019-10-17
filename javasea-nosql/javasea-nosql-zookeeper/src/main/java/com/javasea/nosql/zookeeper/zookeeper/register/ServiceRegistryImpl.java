package com.javasea.nosql.zookeeper.zookeeper.register;

import org.apache.zookeeper.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@Component
public class ServiceRegistryImpl implements ServiceRegistry, Watcher {

    private static CountDownLatch latch = new CountDownLatch(1);
    private ZooKeeper zk;
    private static final int SESSION_TIMEOUT=5000;
    private static final String REGISTRY_PATH = "/registry";

    public ServiceRegistryImpl() {
    }

    public ServiceRegistryImpl(String zkServers) {
        try {
            zk = new ZooKeeper(zkServers,SESSION_TIMEOUT,this);
            latch.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void register(String serviceName, String serviceAddress) {
        try {
            String registryPath = REGISTRY_PATH;
            if (zk.exists(registryPath, false) == null) {
                zk.create(registryPath, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
            //创建服务节点（持久节点）
            String servicePath = registryPath + "/" + serviceName;
            if (zk.exists(servicePath, false) == null) {
                zk.create(servicePath, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
            //创建地址节点
            String addressPath = servicePath + "/address-";
            String addressNode = zk.create(addressPath, serviceAddress.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void process(WatchedEvent watchedEvent) {
        if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
            latch.countDown();
        }
    }

    @Override
    public ArrayList<String> getValue(String path){

        String data = null;
        ArrayList<String> array = new ArrayList<String>();
        try {
            List<String> children = zk.getChildren(path, this);
            ;
            for (String child: children
                 ) {
                data = new String(zk.getData(path+"/"+child,this,null));
                array.add(data);
            }

        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return array;
    }
}
