package com.iee.reflect;

import com.iee.entity.UserDto;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: ReflectTest
 * @Description:
 * @author 龙小南
 * @email longxiaonan@163.com
 * @date 2017年9月10日 上午12:00:05
 * @version 1.0
 */
public class ReflectUtils {
	/**
	 * @author Joe
	 * @Name: getClass
	 * @Description: 范类转换，转换成对应的对象
	 * @Parameters: Class tClass 范类
	 * @Return: 返回对象
	 */
	 public static Class getClazz(Class tClass) {
	     ParameterizedType pt =
	         (ParameterizedType) tClass.getGenericSuperclass();
	     Class entity = (Class)pt.getActualTypeArguments()[0];
	     return entity;
	 }


	/**
	 * 获取指定对象的所有属性  名和值
	 * @param object java对象
	 * @return java对象中的属性组成的key和value
	 * @throws Exception
	 */
	public static Map<String, Object> getObjectValue(Object object) throws Exception {
		//将属性和值封装成map返回
		Map<String, Object> resultMap = new HashMap<>();
		if (object != null) {// if (object!=null ) ----begin
			// 拿到该类
			Class<?> clz = object.getClass();
			// 获取实体类的所有属性，返回Field数组
			Field[] fields = clz.getDeclaredFields();
			for (Field field : fields) {// --for() begin
				String fieldName = field.getName();
				String fieldType = field.getGenericType().toString();
				// 如果类型是String
				if (fieldType.equals("class java.lang.String")) { // 如果type是类类型，则前面包含"class
					// 拿到该属性的gettet方法
					Method m = (Method) object.getClass().getMethod("get" + getMethodName(fieldName));
					String val = (String) m.invoke(object);// 调用getter方法获取属性值
					if (val != null) {
						System.out.println("String type:" + val);
						resultMap.put(fieldName, val);
					}
					continue;
				}
				// 如果类型是Date
				if (fieldType.equals("class java.util.Date")) {
					Method m = (Method) object.getClass().getMethod("get" + getMethodName(fieldName));
					Date val = (Date) m.invoke(object);
					if (val != null) {
						System.out.println("Date type:" + val);
						resultMap.put(fieldName, val);
					}
					continue;
				}
				// 如果类型是Integer
				if (fieldType.equals("class java.lang.Integer") || fieldType.equals("int")) {
					Method m = (Method) object.getClass().getMethod("get" + getMethodName(fieldName));
					Integer val = (Integer) m.invoke(object);
					if (val != null) {
						System.out.println("Integer type:" + val);
						resultMap.put(fieldName, val);
					}
					continue;
				}
				// 如果类型是Double
				if (fieldType.equals("class java.lang.Double") || fieldType.equals("double")) {
					Method m = (Method) object.getClass().getMethod("get" + getMethodName(fieldName));
					Double val = (Double) m.invoke(object);
					if (val != null) {
						System.out.println("Double type:" + val);
						resultMap.put(fieldName, val);
					}
					continue;
				}
				// 如果类型是Boolean 是封装类
				if (fieldType.equals("class java.lang.Boolean")) {
					Method m = (Method) object.getClass().getMethod(fieldName);
					Boolean val = (Boolean) m.invoke(object);
					if (val != null) {
						System.out.println("Boolean type:" + val);
						resultMap.put(fieldName, val);
					}
					continue;
				}
				// 如果类型是boolean 基本数据类型不一样 这里有点说名如果定义名是 isXXX的 那就全都是isXXX的
				// 反射找不到getter的具体名
				if (fieldType.equals("boolean")) {
					Method m = (Method) object.getClass().getMethod(fieldName);
					Boolean val = (Boolean) m.invoke(object);
					if (val != null) {
						System.out.println("boolean type:" + val);
						resultMap.put(fieldName, val);
					}
					continue;
				}
				// 如果类型是Short
				if (fieldType.equals("class java.lang.Short") || fieldType.equals("short")) {
					Method m = (Method) object.getClass().getMethod("get" + getMethodName(fieldName));
					Short val = (Short) m.invoke(object);
					if (val != null) {
						System.out.println("Short type:" + val);
						resultMap.put(fieldName, val);
					}
					continue;
				}
				// 如果还需要其他的类型请自己做扩展
			} // for() --end

		} // if (object!=null ) ----end
		return resultMap;
	}

	// 把一个字符串的第一个字母大写、效率是很高
	private static String getMethodName(String fildeName) throws Exception {
		byte[] items = fildeName.getBytes();
		items[0] = (byte) ((char) items[0] - 'a' + 'A');
		return new String(items);
	}

	public static void main(String[] args) {
		Class clazz = ReflectUtils.getClazz(UserDto.class);
		System.out.println(clazz);
	}
}
