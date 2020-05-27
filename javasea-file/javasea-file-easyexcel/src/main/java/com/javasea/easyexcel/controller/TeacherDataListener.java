package com.javasea.easyexcel.controller;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.javasea.easyexcel.entity.Teacher;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
