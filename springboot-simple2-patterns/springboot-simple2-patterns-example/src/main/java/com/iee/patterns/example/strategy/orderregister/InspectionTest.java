package com.iee.patterns.example.strategy.orderregister;

import com.iee.patterns.example.PatternsExampleApplication;
import com.iee.patterns.example.strategy.orderregister.common.InspectionConstant;
import com.iee.patterns.example.strategy.orderregister.solver.InspectionSolver;
import com.iee.patterns.example.strategy.orderregister.solver.init.InspectionSolverChooser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= PatternsExampleApplication.class)// 指定spring-boot的启动类
public class InspectionTest {

    @Autowired
    private InspectionSolverChooser chooser;

    @Test
    public void test() throws Exception{
        //准备数据
        String taskType = InspectionConstant.INSPECTION_TASK_TYPE_BATCH_CHANGE_WAREHOUSE;
        Long orderId = 12345L;
        Long userId = 123L;
        //获取任务类型对应的solver
        InspectionSolver solver = chooser.choose(taskType);
        if (solver == null) {
            throw new RuntimeException("任务类型暂时无法处理!");
        }
        //调用不同solver的方法进行处理
        solver.solve(orderId,userId);
    }
}

