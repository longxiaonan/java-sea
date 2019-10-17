package com.javasea.scene.distributed.locks.zookeeper.zkclientimpl;

import org.I0Itec.zkclient.ZkClient;

public class LocksTest {


    public static void main(String[] args) {

        ZkClient zk = new ZkClient("192.168.1.231:2181", 5 * 10000);

        for (int i = 0; i < 20; i++) {

            String name = "thread" + i;
            Thread thread = new Thread(() -> {
                Locks myDistributedLock = new Locks(zk, name);
                myDistributedLock.lock();
                try {
                    Thread.sleep(1 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                myDistributedLock.unlock();
            });
            thread.start();
        }

    }
}
