package com.javasea.scene.distributed.locks.zookeeper.zkclientimpl;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Locks {


    private ZkClient zkClient;
    private String name;
    private String currentLockPath;
    private CountDownLatch countDownLatch;

    private static final String PARENT_LOCK_PATH = "/distribute_lock";

    public Locks(ZkClient zkClient, String name) {
        this.zkClient = zkClient;
        this.name = name;
    }

	/**
	 * 1.zkClient.exists先判断父节点是否存在，不存在就创建，zookeeper可以保证只会创建成功一次
     * 2.在当前目录下zkClient.createEphemeralSequential创建临时有序节点，再判断当前目录下此节点是否为序号最小的，
     *   如果是，成功获取锁，否则的话拿比自己小的节点，并做监听
     * 3.waitForLock等待比自己小的节点，subscribeDataChanges监听一个节点的变化，handleDataDeleted里面再次做checkMinNode的判断
     * 4.监听完毕后，再判断一次此节点是否存在，因为在监听的过程中有可能之前小的那个节点重新释放了锁，
     *   如果之前节点不存在的话，无需在这里等待，这里的等待是通过countDownLatch实现的
	 **/
    public void lock() {
    	//判断父节点是否存在，不存在就创建
        if (!zkClient.exists(PARENT_LOCK_PATH)) {
            try {
            	//多个线程只会成功建立一次，zookeeper可以保证只会创建成功一次
                zkClient.createPersistent(PARENT_LOCK_PATH);
            } catch (Exception ignored) {
            }
        }
        //创建当前目录下的临时有序节点
        currentLockPath = zkClient.createEphemeralSequential(PARENT_LOCK_PATH + "/", System.currentTimeMillis());
        //校验是否最小节点
        checkMinNode(currentLockPath);
    }

	//解锁
    public void unlock() {
        System.out.println("delete : " + currentLockPath);
        zkClient.delete(currentLockPath);
    }


    private boolean checkMinNode(String lockPath) {
		//获取当前目录下所有子节点
        List<String> children = zkClient.getChildren(PARENT_LOCK_PATH);
        Collections.sort(children);
        int index = children.indexOf(lockPath.substring(PARENT_LOCK_PATH.length() + 1));
        if (index == 0) {
            System.out.println(name + "：success");
            if (countDownLatch != null) {
                countDownLatch.countDown();
            }
            return true;
        } else {
            String waitPath = PARENT_LOCK_PATH + "/" + children.get(index - 1);
            //等待前一个节点释放的监听
            waitForLock(waitPath);
            return false;
        }
    }


    private void waitForLock(String prev) {
        System.out.println(name + " current path :" + currentLockPath + "：fail add listener" + " wait path :" + prev);
        countDownLatch = new CountDownLatch(1);
        zkClient.subscribeDataChanges(prev, new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object o) throws Exception {

            }

            @Override
            public void handleDataDeleted(String s) throws Exception {
                System.out.println("prev node is done");
                checkMinNode(currentLockPath);
            }
        });
        if (!zkClient.exists(prev)) {
            return;
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        countDownLatch = null;
    }
}
