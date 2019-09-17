package com.zhirui.lmwy.wms.demo.web.controller;

import com.google.common.collect.Maps;
import com.zhirui.lmwy.common.converter.StringToDateConverter;
import com.zhirui.lmwy.wms.demo.web.entity.Student;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;

/**
 * @ClassName TestDateConverter
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2019/9/11 0011 10:23
 */
@RestController
public class TestDateConverterAndJson {

    /**
     * 未处理日期类型返回值：
     * {
     *   "email": "aa@qq.com",
     *   "birth": "2019-09-11T02:34:54.219+0000",
     *   "createTime": "2019-09-11T10:34:54.219"
     * }
     * 可以通过如下两种方式处理
     * 方式一：
     ** `@DatetimeFormat(pattern="yyyy-MM-dd")` 是将String转换成Date，一般前台给后台传值时用
     ** `@JsonFormat(pattern="yyyy-MM-dd")` 将Date转换成String  一般后台传值给前台时
     * 方式二：
     * 在common包的com.zhirui.lmwy.common.converter配置了通用的 参数转换器，如果是日期类型的话相当于方式一的@DatetimeFormat，且属性上的@DatetimeFormat不再起作用。
     * http://localhost:8080/testDateConverter?createTime=123455 可以将`123455`通过转换类{@link StringToDateConverter}转换成 具体的日期
     *
     *  如果对字段单独 serializer 和 deserializer 参考：https://www.cnblogs.com/jifeng/p/9700911.html
     *
     * 测试：
     * 失败：
     * http://localhost:8080/testDateConverter?birth=2019/09/11
     * 成功：
     * http://localhost:8080/testDateConverter?birth=2019-09-11
     */
    //请求的时候converter 生效，返回的时候 serializer生效
    @GetMapping("testDateConverter")
    public Student testDateConverter(Student student){
        System.out.println(student);
        Student s = new Student();
        s.setBirth(new Date());
        s.setCreateTime(LocalDateTime.now());
        return s;
    }

    //请求的时候 converter 生效，返回的时候 serializer生效
    @GetMapping("testDateConverter/{birth}")
    public Student testDateConverter(@PathVariable Date birth){
        System.out.println(birth);
        Student s = new Student();
        s.setBirth(new Date());
        s.setCreateTime(LocalDateTime.now());
        return s;
    }

    //@RequestBody 请求的时候 deserializer 生效，返回的时候 serializer 生效
    @PostMapping("testDateConverter2")
    public Student testDateConverter2(@RequestBody Student student){
        System.out.println(student);
        Student s = new Student();
        s.setBirth(new Date());
        s.setCreateTime(LocalDateTime.now());
        return s;
    }

    @GetMapping("testJson2")
    public HashMap<String, Object> testJson2(Student student){
        System.out.println(student);
        HashMap<String, Object> map = Maps.newHashMap();
        map.put("birth", new Date());
        map.put("birth2", LocalDateTime.now());
        map.put("age", 18);
        return map;
    }

}
