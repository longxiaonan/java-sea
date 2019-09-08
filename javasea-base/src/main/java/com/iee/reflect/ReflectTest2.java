package com.iee.reflect;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

class param<T1, T2> {

    private Class<T1> entityClass;
    public param () throws InstantiationException, IllegalAccessException{
    	System.out.println("getClass() == " + getClass());  	//getClass() == class reflect.ReflectTest2
        Type type = getClass().getGenericSuperclass();
        System.out.println("type = " + type);  					//type = reflect.param<reflect.MyClass, reflect.MyInvoke>
        Type trueType = ((ParameterizedType)type).getActualTypeArguments()[0];
        System.out.println("trueType1 = " + trueType);  		//trueType1 = class reflect.MyClass
        trueType = ((ParameterizedType)type).getActualTypeArguments()[1];
        System.out.println("trueType2 = " + trueType);  		//trueType2 = class reflect.MyInvoke
        this.entityClass = (Class<T1>)trueType;
        System.out.println("entityClass = " + entityClass);		//entityClass = class reflect.MyInvoke
        System.out.println(entityClass.newInstance());			//获取到泛型类MyInvoke后实例化, 需要一级级抛出异常

        B t = new B();
        type = t.getClass().getGenericSuperclass();
        System.out.println(); //reflect.param<T1, T2>.A
        System.out.println("B is A's super class :" + ((ParameterizedType)type).getActualTypeArguments().length);  //B is A's super class :0
    }

    class A {}
    class B extends A {}
}

public class ReflectTest2 extends param<MyClass, MyInvoke>{
    public ReflectTest2() throws InstantiationException, IllegalAccessException {
		super();
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        ReflectTest2 classDemo = new ReflectTest2();
    }
}


class MyClass {

}

class MyInvoke {

}
