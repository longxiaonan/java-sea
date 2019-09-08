package com.iee.sort;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;

import java.util.Comparator;

public class Girl1 implements Comparable<Girl1>{
	private String name;
	private int age;
	private int height;

	@Override
	public String toString() {
		return "Girl [name=" + name + ", age=" + age + ", height=" + height+"]";
	}

	public Girl1() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Girl1(String name, int height) {
		super();
		this.name = name;
		this.height = height;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	final Comparator<String> strLengthComparator = new Comparator<String>() {
		@Override
		public int compare(String str1, String str2) {
//			return str2.length() - str1.length();//倒序, 最长的排前面
			return str1.length() - str2.length();//正序, 最短的排前面
		}
	};

	// 上面的比较使用这个比较器：
	@Override
	public int compareTo(Girl1 girl) {
		return ComparisonChain.start() // 链式比较,在第一个非0处返回
				.compare(this.name, girl.getName(), Ordering.from(strLengthComparator))
//				.compare(girl.getHeight(),this.height)//倒序
				.compare(this.height,girl.getHeight())//正序
				.result();
	}

}
