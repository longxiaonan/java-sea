package com.iee.concurrent.queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
    * @ClassName: Set
	* @Description: set相关操作
    * @author 龙小南
    * @email longxiaonan@163.com
    * @date 2017年8月9日 下午1:27:59
    * @version 1.0
    */
public class LinkedBlockingQueueTest {
	public static void main(String[] args) throws Exception {
		LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
		queue.add("aaa");
		queue.add("bbb");
		queue.add("ccccc");
		queue.add("dddd");
		queue.add("eeee");
		queue.add("fff");
		System.out.println(queue);
		for (String s : queue) {
			System.out.println(">>"+s);
		}
		System.out.println(queue.poll());
		System.out.println(queue.poll());
		System.out.println(queue.poll());
		System.out.println(queue);
	}
}
