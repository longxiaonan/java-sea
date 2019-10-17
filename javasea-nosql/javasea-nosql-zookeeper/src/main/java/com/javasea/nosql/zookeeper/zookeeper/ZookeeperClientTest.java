package com.javasea.nosql.zookeeper.zookeeper;

import org.apache.zookeeper.*;

import java.io.IOException;

/**
 * @ClassName ZookeeperClientTest
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2019/10/13 0013 21:43
 */
public class ZookeeperClientTest {
    public static void main(String[] args) throws IOException, KeeperException, InterruptedException
    {
        // 创建一个与服务器的连接 需要(服务端的 ip+端口号)(session过期时间)(Watcher监听注册)
        ZooKeeper zk = new ZooKeeper("192.168.1.231:2181", 3000, new Watcher()
        {
            // 监控所有被触发的事件
            @Override
            public void process(WatchedEvent event)
            {
                System.out.println(event.toString());
            }
        });
        System.out.println("OK!");

        // 创建一个目录节点
        /**
         * CreateMode:
         *       PERSISTENT (持续的，相对于EPHEMERAL，不会随着client的断开而消失)
         *       PERSISTENT_SEQUENTIAL（持久的且带顺序的）
         *       EPHEMERAL (短暂的，生命周期依赖于client session)
         *       EPHEMERAL_SEQUENTIAL  (短暂的，带顺序的)
         */
        zk.create("/path01", "data01".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        // 创建一个子目录节点
        zk.create("/path01/path01", "data01/data01".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println(new String(zk.getData("/path01", false, null)));

        // 取出子目录节点列表
        System.out.println(zk.getChildren("/path01", true));

        // 创建另外一个子目录节点
        zk.create("/path01/path02", "data01/data02".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println(zk.getChildren("/path01", true));

        // 修改子目录节点数据
        zk.setData("/path01/path01", "data01/data01-01".getBytes(), -1);
        byte[] datas = zk.getData("/path01/path01", true, null);
        String str = new String(datas, "utf-8");
        System.out.println(str);

        // 删除整个子目录 -1代表version版本号，-1是删除所有版本
        zk.delete("/path01/path01", -1);
        zk.delete("/path01/path02", -1);
        zk.delete("/path01", -1);
        System.out.println(str);

        Thread.sleep(15000);
        zk.close();
        System.out.println("OK");
    }
}
