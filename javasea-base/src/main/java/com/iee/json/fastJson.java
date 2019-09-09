package com.iee.json;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iee.common.entity.User;

import java.util.*;

/**
    * @ClassName: fastJson
	* @Description:
    * @author 龙小南
    * @email longxiaonan@163.com
    * @date 2017年8月31日 下午9:31:07
    * @version 1.0
    */
public class fastJson {
	public static void main(String[] args) {
		JSONObject jo = new JSONObject();
		jo.put("time", "");
		Date date = jo.getDate("time");
		System.out.println(date);

		JSONObject jo1 = new JSONObject();
		jo1.put("aa", date);
		System.out.println(jo1);

		User user  = new User("logn", 18);
		JSONObject json = (JSONObject) JSONObject.toJSON(user);
		System.out.println(json);
//		jsonTranslate();
		/*JSONObject jo = new JSONObject();
		jo.put("aa", 2L);
		String object = jo.getString("aa");//任何基本数据类型put后都可以通过getString获取
		System.out.println(object);
		*/
//		testPutMap();
//		testPutArrayListObj();
//		stringFromat();//string转json时, string的类型测试
//		jsonTranslate();

//		String aa = "{\"EVENT_TYPE\": 10002,\"LOCATION\": [\"ISVALID\",\"LONGITUDE\", \"STATE\",\"ISNS\",\"LATITUDE\"],\"DATATAG\": \"RTINFO\",\"DEVCODE\": \"60100000117360979\"}";
//		JSONObject parse = JSONObject.parseObject(aa);
//		JSONArray array = parse.getJSONArray("LOCATION");
//		array.add("heheh2");
//		System.out.println(array);
	}

	private static void jsonTranslate() {
		//json和json类型的字符串的转换
		String str = "{\"array\":[\"d\",\"e\"]}";
		JSONObject.parseObject(str);//将string格式的字符串转json
		JSONObject obj = new JSONObject();
		obj.toJSONString();

		//json和实体对象的转换
		User user  = new User("logn", 18);
		JSONObject json = (JSONObject) JSONObject.toJSON(user);//对象转json
		System.out.println(json);//{"age":18,"name":"logn"}
		User parseUser = JSONObject.parseObject(json.toJSONString(), User.class);//string转对象
		System.out.println(parseUser);

		//json和map的转换
		@SuppressWarnings("unchecked")
		Map<String, Object> map = JSONObject.parseObject(json.toJSONString(), Map.class);
		json.putAll(map);

	}

	private static void stringFromat() {
		String str = "12";
		JSONObject parse = JSONObject.parseObject(str);
		System.out.println(parse); //str=""||str=null, parse=null;str = "aaa", 格式不正确报异常
	}
	private static void testPutArrayListObj() {
		JSONObject object = new JSONObject();
		User user = new User();
		user.setAge(18);
		user.setName("张三");
		 //如果是实体类对象,则会将实体类组装成JSONObject放进去
		object.put("user", user);
		List<User> users = new ArrayList<>();
		users.add(user);
		// 构造List格式数据, 放入到JSONObject中
		object.put("list", users); // List

		// 构造JSONArray格式的数据
		JSONArray array = new JSONArray();
		array.add("d");
		array.add("e");
		object.put("array", array);
		System.out.println(object);

		//list和jsonArray转换
		JSONArray ja = new JSONArray();
		ja.addAll(users);
		System.out.println(ja.toJSONString());
		String s = ja.toJSONString();
		List<User> parseArray = JSONArray.parseArray(s, User.class);
		System.out.println(parseArray);
	}

	private static void testPutMap() {
		JSONObject object = new JSONObject();
		object.put("key1", "value1");
		object.put("key2", 1); // int
		object.put("key3", 35.7); // double
		object.put("numberString", "55.8");
		object.put("date", new Date());
		Map<String, Object> map = new HashMap<String, Object>();
		List<String> list = new ArrayList<>();
		list.add("11");
		list.add("22");
		list.add("33");

		map.put("map_key1", "map_value1");
		map.put("map_key2", "map_value2");
		map.put("aa", 11);
		map.put("bb", true);
		map.put("cc", list);
		object.put("key4", map);// 如果value是Map,则添加里层JSONObject, key为key4</span>
		object.putAll(map);// 如果是通过putAll,则会将map中的元素循环依次作为Object对象插入JSONObject数据
		System.out.println(object);
	}
}
