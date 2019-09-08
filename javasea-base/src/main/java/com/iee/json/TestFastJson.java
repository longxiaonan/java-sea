package com.iee.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.iee.entity.Student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName TestFastJson
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2019/5/30 0030 20:40
 */
public class TestFastJson {
    public static void main(String[] args) {
//        add2Json();
        conversionType();
    }

    private static void conversionType() {
        Student user = new Student();
        user.setName("longxn");
        user.setAge(18);
        user.setSex("男");
        JSONObject o1 = new JSONObject();
        List listUser = new ArrayList();
        listUser.add(user);

        //1. bean to json
        //方式一
        String userString = JSONObject.toJSONString(user);
        JSONObject jsonObject = JSONObject.parseObject(userString);
        System.out.println(jsonObject);
        //方式二
        JSONObject jsonObject1 = (JSONObject) JSONObject.toJSON(user);
        System.out.println(jsonObject1);

        //2. json to bean
        //第一种方式,使用TypeReference<T>类,由于其构造方法使用protected进行修饰,故创建其子类
        Student student = JSONObject.parseObject(jsonObject.toJSONString(), new TypeReference<Student>() {});
        System.out.println(student);
        //第二种方式,使用Gson的思想
        Student student1 = JSONObject.parseObject(jsonObject.toJSONString(), Student.class);
        System.out.println(student1);

        //3. bean to map
        Map map = JSONObject.parseObject(userString, Map.class);
        System.out.println("map>>"+map);

        //4. map to bean
//        JacksonUtils.map2pojo(map2pojo)

        //5. map to json
        JSONObject o = (JSONObject) JSON.toJSON(map);
        System.out.println("map2json>>"+o);

        //6. json to map
        Map map2 = JSONObject.parseObject(userString, Map.class);

        //7. list to array
        //方式一
        String usersString = JSONArray.toJSONString(listUser);
        JSONArray jsonArray = JSONArray.parseArray(usersString);
        //方式二
        JSONArray jsonArray1 = (JSONArray) JSONArray.toJSON(listUser);

        //8. array to list
        List<Student> users = JSONArray.parseArray(usersString, Student.class);



    }

    private static void add2Json() {
        JSONObject object = new JSONObject();
        object.put("key1", "value1"); // String
        object.put("key2", 1); // int
        object.put("key3", 35.7); // double
        object.put("numberString", "55.8");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("map_key1", "map_value1");
        map.put("map_key2", "map_value2");
        object.put("key4", map); // 如果是Map,则添加里层JSONObject
        object.putAll(map); // 如果是通过putAll,则会循环依次作为Object对象插入JSONObject数据
        Student user = new Student();
        user.setAge(18);
        user.setName("张三");
        //如果是实体类对象,则会将实体类组装成JSONObject放进去
        object.put("user", user);
        List<Student> users = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Student u = new Student();
            u.setAge(i + 18);
            u.setSex(i % 2 == 0 ? "男" : "女");
            users.add(u);
        }
        object.put("list", users); // List
        // 构造JSONArray格式的数据
        JSONArray array = new JSONArray();
        array.add("d");
        object.put("array", array);
        System.out.println(object);
    }
}
