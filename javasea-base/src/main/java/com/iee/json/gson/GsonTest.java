package com.iee.json.gson;

import com.google.gson.*;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.reflect.TypeToken;
import com.iee.common.entity.Result;
import com.iee.common.entity.Result2;
import com.iee.common.entity.User;
import org.junit.Test;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ClassName GsonTest
 * @Description Gson测试类，参考：https://juejin.im/post/5d764dcc6fb9a06b130f4b2a?utm_source=gold_browser_extension#heading-6
 * @Author longxiaonan@163.com
 * @Date 2019/9/10 0010 14:21
 */
public class GsonTest {

    /** 简单对象的序列化成 JSON String
     *
     * 默认的 Gson 对象行为序列化对象时会将 null 值的字段忽略，而 com.google.gson.GsonBuilder#serializeNulls 方法
     * 将允许 Gson 对象序列化 null 字段；并且正常序列化后的 JSON 字符串是紧凑格式，节省字符串内存，
     * 使用 com.google.gson.GsonBuilder#setPrettyPrinting 方法之后最终输出的 JSON 字符串是更易读的格式。
     * */
    @Test
    public void pojo2JSONString() {
        //自动过滤掉为空的字符串
        Gson gson = new Gson();
        Result result = new Result(200, "成功", null);
        String json = gson.toJson(result);
        System.out.println("json is " + json);

        Gson buildedGson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        String buildedJson = buildedGson.toJson(result);
        System.out.println("buildedJson is " + buildedJson);
    }

    /** JosnObject 生成 JSON String
     *
     * JsonObject 使用 addProperty(property,value) 方法只能用来添加 String，Number，
     * Boolean，Character这四类数据， 因为内部是调用 com.google.gson.JsonObject#add,
     * 将 value 封装成了 JsonPrimitive 对象，然后保存到了内部自定义的 LinkedTreeMap 集合变量 members 中；
     * 如果需要在 JsonObject 对象上添加其他对象时，就需要直接使用 add(String property, JsonElement value)
     * 方法添加一个 JsonElement 对象。这里的 JsonElement 是一个抽象类，JsonObject 和 JsonPrimitive 都继承了JsonElement，
     * 所以我们最终通过另外的 JsonObject 对象来作为原 JsonObject 上的属性对象
     * */
    @Test
    public void JSONObject2JSONString() {
        Gson gson = new Gson();
        JsonObject jsonObject = new JsonObject();
        JsonObject nestJsonObject = new JsonObject();
        nestJsonObject.addProperty("username", "one");
        nestJsonObject.addProperty("score", 99);
        jsonObject.add("data", nestJsonObject);
        String toJson2 = gson.toJson(jsonObject);
        System.out.println(toJson2);
        // {"code":400,"message":"参数错误","data":{"username":"one","score":99}}

    }

    /** 这里主要使用方法是 com.google.gson.Gson#fromJson，它最基础的用法就是fromJson(String json, Class<T> classOfT)
     *  尝试将 JSON 字符串转为指定 Class 的对象，如果转换失败，就会抛出 JsonSyntaxException 异常。
     */
    @Test
    public void JSONString2Pojo() {
        String json = "{\"code\":400,\"message\":\"参数错误\"}";
//        String json = "{\"code\":200,\"message\":\"操作成功\",\"data\":{\"name\": \"one\",\"pId\": \"image.jpg\"" +
//                "}}";
        Result result = new Gson().fromJson(json, Result.class);
        System.out.println(result);
    }

    /** 泛型对象的反序列化, 且嵌套 */
    @Test
    public void JSONString2Obj2() {
        String json = "{\"code\":200,\"message\":\"操作成功\",\"data\":{\"name\": \"one\",\"pId\": \"image.jpg\"" +
                "}}";
        Type type = new TypeToken<Result2<User>>(){}.getType();
        Result2<User> result = new Gson().fromJson(json, type);
        System.out.println(result);
    }

    /** 需要注意的是转换后的 Map 对象真实类型并不是我们经常用的 HashMap，
        而是 Gson 自定义集合LinkedTreeMap ，它实现Map 接口来存储键值对，在新增和删除上实现上进行了优化，
        并且将存储键值对的顺序作为遍历顺序，也就是先存入的先被遍历到。除此之外，
        JSON 字符串里的数值型数据都会转转换为 Double 类型，而 true/false 数据被会被转换成 Boolean 类型，
        具体判断依据可以参考 com.google.gson.internal.bind.ObjectTypeAdapter#read 方法的实现
     */
    @Test
    public void JSONString2Map() {
        String jsonString = "{'employee.name':'one','employee.salary':10}";
        Gson gson = new Gson();
        Map map = gson.fromJson(jsonString, Map.class);
    }

    @Test
    public void JSONString2Array() {
        Gson gson = new Gson();
        int[] ints = {1, 2, 3, 4, 5};
        String[] strings = {"abc", "def", "ghi"};
        String s = gson.toJson(ints);// [1,2,3,4,5]

        String s1 = gson.toJson(strings);// ["abc", "def", "ghi"]
        String[] strings1 = gson.fromJson(s1, String[].class);

        int[] ints2 = gson.fromJson("[1,2,3,4,5]", int[].class);
    }

    /** fromJson不能直接用List.class， 而是需要用下面的方式 */
    @Test
    public void JSONString2List() {
        Gson gson = new Gson();
        String inputString = "[{\"id\":1,\"name\":\"one\"},{\"id\":2,\"name\":\"two\"}]";
        Type type = new TypeToken<List<User>>(){}.getType();
        List<User> outputList = gson.fromJson(inputString, type);
        System.out.println(outputList);
        String id = outputList.get(0).getId();
        System.out.println(id);
    }

    /** 日期自定义序列化 */
    @Test
    public void test_dateSerializer() {
        MyObject myObject = new MyObject(new Date(), "one");
        Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new DateSerializer()).create();
        String json = gson.toJson(myObject); //{\"date\":\"2019-09-08\",\"name\":\"one\"}
        System.out.println(json);
    }

    /** 日期自定义序列化, 采用 @JsonAdapter 注解方式 */
    @Test
    public void test_dateSerializer2() {
        MyObject2 myObject = new MyObject2(new Date(), "one");
        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(myObject); //{\"date\":\"2019-09-08\",\"name\":\"one\"}
        System.out.println(json);
    }

    class DateSerializer implements JsonSerializer<Date> {
//        SimpleDateFormat dateTime = new SimpleDateFormat("yyyy-MM-dd mm:HH:ss");
        @Override
        public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(new SimpleDateFormat("yyyy-MM-dd mm:HH:ss").format(src));
        }
    }

    class MyObject {
        private Date date;
        private String name;

        public MyObject(Date date, String name) {
            this.date = date;
            this.name = name;
        }

        public MyObject() {
        }
    }

    class MyObject2 {
        @JsonAdapter(DateSerializer.class)
        private Date date;
        private String name;

        public MyObject2(Date date, String name) {
            this.date = date;
            this.name = name;
        }

        public MyObject2() {
        }
    }

}
