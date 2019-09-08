package com.iee.sort;

public class Girl2{
	private String name;
	private int age;
	private int height;

	@Override
	public String toString() {
		return "Girl [name=" + name + ", age=" + age + ", height=" + height+"]";
	}

	public Girl2() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Girl2(String name, int height) {
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

}
