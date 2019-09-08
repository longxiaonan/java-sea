package com.iee.guava;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class LoadingCacheDemo {
	public static void main(String[] args) throws ExecutionException {
		// 方法一
		// 创建本地缓存，当本地缓存不命中时，调用load方法，返回结果，再缓存结果。
		LoadingCache<String, Object> loadingCache = CacheBuilder.newBuilder().expireAfterWrite(10, TimeUnit.SECONDS).maximumSize(10000)
				.build(new CacheLoader<String,Object>() {
					@Override
					public Object load(String key) throws Exception {
						return "qqqqq";
					}
				});

		System.out.println(loadingCache.get("test"));
		System.err.println(loadingCache.get("test2"));
		// 方法二
		// 创建缓存对像
		Cache<String, Object> cache = CacheBuilder.newBuilder().expireAfterWrite(10, TimeUnit.SECONDS).maximumSize(10000).build();

		// 调用缓存中的get方法，当缓存命中时直接返回结果，当不命中时，通过给定的Callable类call方法 返回结果，再缓存。这个方法
		// 到更灵活，可以用一个cache对象缓存多种不同的数据，只要用不同的Callable对象就行。
		Object object = cache.get("111", new Callable<Object>() {
			@Override
			public Object call() throws Exception {
				System.out.println("经过 call()");
				return "value.";
			}
		});
		System.out.println(object);
	}

	public static String getString(String key) {
		System.out.println("经过 getString()");
		return key + "--Test";
	}
}
