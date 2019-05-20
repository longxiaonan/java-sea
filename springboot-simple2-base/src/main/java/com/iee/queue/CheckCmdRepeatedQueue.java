package com.iee.queue;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName CheckCmdRepeatedQueue
 * @Description 通过ArrayBlockingQueue实现FIFO队列，当满的时候再添加会自动出队
 * @Author longxiaonan@163.com
 * @Date 2019/5/20 0020 12:39
 */
public class CheckCmdRepeatedQueue {

    private static AtomicInteger maxSize = new AtomicInteger(100);

    static ArrayBlockingQueue arrayBlockingQueue = new ArrayBlockingQueue(maxSize.get());
    public static void main(String[] args) {

        for(int i = 0 ; i < 10 ; i++){
            boolean add = getInst().add(i + "");
            System.out.println(arrayBlockingQueue.contains(i + ""));
        }
        System.out.println(arrayBlockingQueue);
    }
    public boolean add(Object a){
        int size = arrayBlockingQueue.size();
        if(size >= maxSize.get()){
            arrayBlockingQueue.remove();
        }
        return arrayBlockingQueue.add(a);
    }
    public boolean contains(Object o){
        return arrayBlockingQueue.contains(o);
    }

    private CheckCmdRepeatedQueue(){}

    private static class Holder{
        private static final CheckCmdRepeatedQueue instance = new CheckCmdRepeatedQueue();
    }

    public static CheckCmdRepeatedQueue getInst(){
        return Holder.instance;
    }



    public void setMaxSize(AtomicInteger maxSize) {
        CheckCmdRepeatedQueue.maxSize = maxSize;
    }
}
