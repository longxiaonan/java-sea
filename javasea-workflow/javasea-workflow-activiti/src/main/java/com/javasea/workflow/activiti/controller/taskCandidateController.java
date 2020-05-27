package com.javasea.workflow.activiti.controller;

import org.activiti.engine.TaskService;
import org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName taskCandidateController
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2020/5/18 0018 22:07
 */
@RestController
public class taskCandidateController {

    @Autowired
    StandaloneProcessEngineConfiguration standaloneProcessEngineConfiguration;

    @GetMapping("candidateTask")
    public void candidateTask(){

        TaskService taskService = standaloneProcessEngineConfiguration.buildProcessEngine().getTaskService();

        // 流程定义key
        String processDefinitionKey = "holiday5";
        // 任务候选人
        String candidateUser = "xiaonan";
        // 查询候选用户的组任务
        List<Task> list = taskService.createTaskQuery().processDefinitionKey(processDefinitionKey)
                .taskCandidateUser(candidateUser)
                .list();
        list.forEach(task -> {
                System.out.println(task.getProcessInstanceId());
                System.out.println(task.getName());
                System.out.println(task.getAssignee()); // act_hi_actinst执行人为null，需要在拾取操作后才有值
                System.out.println(task.getId());

                // 拾取组任务，本质就是改变assign
                taskService.claim(task.getId(), candidateUser);

                    // 归还任务 先校验后归还
//                Task task = taskService.createTaskQuery().taskId(taskId).taskAssignee(candidateUser).singleResult();
//                if(task != null) {
                      // 归还任务 归还任务就是一个候选人设置为空的过程
//                    taskService.setAssignee(task.getId(), null);
                    // 交接任务 负责人将任务交接给候选人lisi，交接任务就是一个候选人拾取用户的过程
//                    taskService.setAssignee(task.getId(), "lisi);
//                }

            System.out.println("任务拾取完毕！");
        });
        // 再次查询候选人的任务
        List<Task> taskList = taskService.createTaskQuery().processDefinitionKey(processDefinitionKey)
                .taskAssignee(candidateUser)
                .list();
        System.out.println(taskList);
        // 完成任务
        taskList.forEach(a -> {
            taskService.complete(a.getId());
        });
    }

}
