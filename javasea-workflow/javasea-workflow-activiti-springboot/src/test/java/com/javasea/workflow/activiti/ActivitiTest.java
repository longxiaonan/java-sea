package com.javasea.workflow.activiti;

import com.javasea.workflow.activiti.config.SecurityUtil;
import org.activiti.api.process.model.ProcessDefinition;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.builders.ProcessPayloadBuilder;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.api.task.model.Task;
import org.activiti.api.task.model.builders.TaskPayloadBuilder;
import org.activiti.api.task.runtime.TaskRuntime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @ClassName ActivitiTest
 * @Description 测试Activiti25张表的生成
 * @Author longxiaonan@163.com
 * @Date 2019/7/12 0012 16:13
 */

public class ActivitiTest extends BaseTest {

    //activiti7新api 实现流程定义相关操作
    @Autowired
    private ProcessRuntime processRuntime;

    //activiti7新api 任务相关操作
    @Autowired
    private TaskRuntime taskRuntime;

    //springsecurity相关的工具类
    @Autowired
    private SecurityUtil securityUtil;

    /**
     * bpm放置到resources/processes就会自动部署
     **/
    @Test
    public void testDefinition(){
        securityUtil.logInAs("zhangsan");
        // 分页查询流程定义信息
        Page<ProcessDefinition> processDefinition = processRuntime.processDefinitions(Pageable.of(0, 10));
        // 查看部署的流程数
        int totalItems = processDefinition.getTotalItems();
        System.out.println("流程定义的个数" + totalItems);
        // 得到当前部署的每一个流程定义信息
        List<ProcessDefinition> content = processDefinition.getContent();
        content.forEach((a) -> {
            System.out.println("流程定义" + a);
        });
    }

    /** 启动流程实例 */
    @Test
    public void testStartInstance(){
        securityUtil.logInAs("zhangsan");

        ProcessInstance processInstance = processRuntime
                .start(ProcessPayloadBuilder
                        .start()
                        .withProcessDefinitionKey("myProcess_1")
                        .build());
    }

    /**
     * 查询任务，并且完成任务
     */
    @Test
    public void testTask(){
        securityUtil.logInAs("zhangsan");
        Page<Task> tasks = taskRuntime.tasks(Pageable.of(0, 10));
        for (Task task : tasks.getContent()){
            System.out.println("任务:" + task);

            //拾取任务
//            taskRuntime.claim(TaskPayloadBuilder.claim().withTaskId(task.getId()).build());
            taskRuntime.complete(TaskPayloadBuilder.complete().withTaskId(task.getId()).build());
        }

        //再次查询新的任务
        tasks = taskRuntime.tasks(Pageable.of(0, 10));
        System.out.println("再次查询任务："+tasks);
    }


}
