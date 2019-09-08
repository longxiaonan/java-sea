package com.iee.guava;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.ExecutionException;

public class GuavaDemo {
	public static void main(String[] args) throws ExecutionException {

		LoadingCache<String, String> cahceBuilder = CacheBuilder.newBuilder().build(new CacheLoader<String, String>() {
			@Override
			public String load(String key) throws Exception {
				String strProValue = "hello " + key + "!";
				return strProValue;
			}
		});
		System.out.println(cahceBuilder.apply("begincode")); // hello begincode!
		System.out.println(cahceBuilder.get("begincode")); // hello begincode!
		System.out.println(cahceBuilder.get("wen")); // hello wen!
		System.out.println(cahceBuilder.apply("wen")); // hello wen!
		System.out.println(cahceBuilder.apply("da"));// hello da!
		cahceBuilder.put("begin", "code");
		System.out.println(cahceBuilder.get("begin")); // code
		System.out.println(cahceBuilder.get("hehe")); // hello hehe!
		cahceBuilder.put("hehe", "buzhidao"); //对指定的key进行赋值
		System.out.println(cahceBuilder.get("hehe")); // buzhidao
	}
}
