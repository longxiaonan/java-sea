package com.iee.reflect.commonDao;


import com.iee.reflect.ReflectUtils;

public class CommonDaoImpl<T> implements ICommonDao<T> {
    //范型转换
    @SuppressWarnings("unchecked")
    private Class entity = (Class) ReflectUtils.getClazz(this.getClass());

    public void save(T t){
    	System.out.println(">>>>>>>"+t);
    	System.out.println(">>>>>>>>>>"+entity);
    }
}
