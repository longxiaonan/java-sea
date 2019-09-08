package com.iee.lambda;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * @ClassName: test
 * @Description:
 * @author 龙小南
 * @email longxiaonan@163.com
 * @date 2017年11月13日 下午8:53:15
 * @version 1.0
 */
public class test {
	public static void main(String[] args) {
		test();
	}

	private static Object print(){
		System.out.println("this is print function");
		return new Object();
	}

	@SuppressWarnings("unused")
	private static String test() {
		Object o = new Object();
		//普通判空方式
//		if(null != o){
//			Class<? extends Object> c = o.getClass();
//			if(null != c){
//				System.out.println(11);
//				return "aa";
//			}
//		}
//		throw new RuntimeException("不能为空");

		o = null;

		//orElseGet
		Optional.ofNullable(o).orElseGet(() -> "aaaa");
		Object aa = Optional.ofNullable(o).orElseGet(t1::new);
		Object bb = Optional.ofNullable(o).orElseGet(t2::new);
		Object cc = Optional.ofNullable(o).orElseGet(() -> print());
		System.out.println(aa);
		System.out.println(bb);
		System.out.println(cc);
		//改写上面的if
		Optional.ofNullable(o).map(b -> b.getClass()).orElseThrow(() -> new RuntimeException("不能为空!!"));

		return null;
	}

}

class t1 implements Supplier<Object> {

	@Override
	public Object get() {
		System.out.println("this is t1 class");
		return new Object();
	}

}


class t2 implements Runnable {

	@Override
	public void run() {
		System.out.println("this is t2 class");
	}

}
