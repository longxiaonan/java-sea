# 手把手教你封装EasyExcel工具类

## 前言

EasyExcel是阿里巴巴开源的excel处理工具，要自己通过实现一个Listener来实现处理，emmm，有点麻烦，那么我们可以写个通用的listener来处理吗？当然是可以的，下面展示我是如何一步步封装成我最满意的样子的~

功能包括导入和导出功能，在项目中导入解析用得比较多，所以我下面主要是对导入方式封装演示~

## 生成我们需要的数据

```java
@Test
public void exportTest() {
    //        准备数据
    List<Teacher> teachers1 = new ArrayList<>();
    teachers1.add(new Teacher(1, "a1", "hhh.jpg", 1));
    teachers1.add(new Teacher(1, "a2", "hhh.jpg", 1));
    teachers1.add(new Teacher(1, "a3", "hhh.jpg", 1));
    teachers1.add(new Teacher(1, "a4", "hhh.jpg", 1));
    teachers1.add(new Teacher(1, "a5", "hhh.jpg", 1));
    teachers1.add(new Teacher(1, "a6", "hhh.jpg", 1));
    String fileName = "d:/wms/aaaa.xls";
    // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
    // 如果这里想使用03 则 传入excelType参数即可
    EasyExcel.write(fileName, Teacher.class).sheet("sheet1").doWrite(teachers1);
}
```



## 最简单导入方式

### 定义Listener实现数据处理

```java
package com.javasea.easyexcel.controller;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.javasea.easyexcel.entity.Teacher;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName TeacherDataListener
 * @Description 监听器这个类不能够被Spring管理，每次使用单独的new出来
 * @Author longxiaonan@163.com
 * @Date 2020/3/23 0023 17:45
 */
public class TeacherDataListener extends AnalysisEventListener<Teacher> {
    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 500;
    /**
     * 这个集合用于接收 读取Excel文件得到的数据
     */
    List<Teacher> list = new ArrayList<Teacher>();
    /**
     * 假设这个是一个DAO，当然有业务逻辑这个也可以是一个service。当然如果不用存储这个对象没用。
     */
//    private TeacherDao teacherDao;

    public TeacherDataListener() {
    }

    /**
     * * 不要使用自动装配  * 在测试类中将dao当参数传进来
     */
//    public TeacherDataListener(TeacherDao teacherDao) {
//        this.teacherDao = teacherDao;
//    }

    /**
     * 这个每一条数据解析都会来调用  *
     */
    @Override
    public void invoke(Teacher teacher, AnalysisContext context) {
        list.add(teacher);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (list.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            list.clear();
        }
    }

    /**
     * 所有数据解析完成了 都会来调用  *
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
    }

    /**
     * 加上存储数据库
     */
    private void saveData() {
        //  在这个地方可以调用dao  我们就直接打印数据了
        System.out.println(list);

    }
}
```

在测试类中执行读取

```java
@Test
public void importTest() {

    String fileName =  "d:/wms/aaaa-无title.xls";

    // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
    /**
         * 参数1 要读取的文件
         * 参数2 要读取的数据对应的实体类类对象
         * 参数3 监听器对象 可以在创建的时候把dao当做参数传进去
         */
    EasyExcel.read(fileName, Teacher.class, new TeacherDataListener()).sheet().doRead();
}
```

通过`TeacherDataListener`来处理数据， 在`TeacherDataListener`的`saveData`方法将数据进行打印。





测试访问：
http://localhost:8080/

参考：
http://easypoi.mydoc.io/#category_50222

https://juejin.im/post/5e608520e51d45272443ef07?utm_source=gold_browser_extension

https://alibaba-easyexcel.github.io/quickstart/read.html

https://github.com/alibaba/easyexcel
