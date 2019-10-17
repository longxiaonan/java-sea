package com.javasea.scene.distributed.locks.zookeeper.curatorimpl;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @ClassName locks
 * curator的分布式锁
 * Curator本身提供了多种锁的实现，这里我们以InterProcessMutex可重入锁为例，
 * lock.acquire()方法获取锁，lock.release()来释放锁，acquire方法也提供了重载的等待时间参数
 *
 * CuratorFrameworkFactory.newClient获取zookeeper的客户端，retryPolicy指定重试策略，开启客户端
 *
 * 1.客户端连接zookeeper，并在/lock下创建临时的且有序的子节点，第一个客户端对应的子节点为/lock/lock-0000000000，
 * 第二个为/lock/lock-0000000001，以此类推。
 * 2.客户端获取/lock下的子节点列表，判断自己创建的子节点是否为当前子节点列表中序号最小的子节点，如果是则认为获得锁，
 * 否则监听刚好在自己之前一位的子节点删除消息，获得子节点变更通知后重复此步骤直至获得锁；
 * 3.执行业务代码；
 * 4.完成业务流程后，删除对应的子节点释放锁。
 * @Author longxiaonan@163.com
 * @Date 2019/10/13 0013 19:53
 */
public class locks {
    public static void main(String[] args) throws Exception {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient("192.168.1.231:2181", retryPolicy);
        //集群模式下的连接方式
        //CuratorFramework client = CuratorFrameworkFactory.newClient("10.21.41.181:2181,10.21.42.47:2181,10.21.49.252:2181", retryPolicy);
        client.start();
        //创建分布式锁, 锁空间的根节点路径为/curatorimpl/lock
        InterProcessMutex lock = new InterProcessMutex(client, "/curatorimpl/lock");
        try {
            lock.acquire();
            //模拟业务
            Thread.sleep(200);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.release();
        }
    }
}
