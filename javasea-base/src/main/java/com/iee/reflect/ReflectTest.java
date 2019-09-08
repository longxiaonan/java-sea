package com.iee.reflect;

import java.util.LinkedHashMap;
import java.util.Map;

/**
    * @ClassName: test
	* @Description:
    * @author 龙小南
    * @email longxiaonan@163.com
    * @date 2017年9月10日 上午12:04:34
    * @version 1.0
    */
public class ReflectTest {
	public static void main(String[] args) throws Exception {
//		User user = new User("longxiaonan", 18);
//		Map<String, Object> map = ReflectUtils.getObjectValue(user);
//		System.out.println(map);
		Map map = new LinkedHashMap<String,Object>();
		map.put("name",	"123");
		map.put("age", 23);
		map.put("sex", "m");
		Map<String, Object> map2 = ReflectUtils.getObjectValue(map);
		System.out.println(map2);
		String name = ReflectTest.class.getName();
		System.out.println(name);
	}
}
