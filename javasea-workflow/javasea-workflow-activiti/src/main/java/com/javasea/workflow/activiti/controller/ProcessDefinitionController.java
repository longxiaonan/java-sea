package com.javasea.workflow.activiti.controller;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

/**
 * @ClassName ProcessDefinitionController
 * @Description 流程定义controller
 * @Author longxiaonan@163.com
 * @Date 2019/8/21 0021 12:38
 */
@RestController
@RequestMapping("")
public class ProcessDefinitionController {

    @Autowired
    StandaloneProcessEngineConfiguration standaloneProcessEngineConfiguration;


    /** 部署流程 涉及到的表：
     * act_re_deployment： 部署信息
     * act_re_procdef：流程定义的一些信息
     * act_ge_bytearray: 流程定义的png和bpmn文件
     * */
    @GetMapping("/deploy")
    public void deploy(){
        //1.获取processEngine实例
        ProcessEngine processEngine = standaloneProcessEngineConfiguration.buildProcessEngine();

        //2.获取repositoryService实例
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //3.进行部署
        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource("diagram/holiday-exclusive.bpmn")//添加bpmn资源
//                .addClasspathResource("diagram/holiday4.png") //添加png资源
                .name("请假流程-流程变量")//部署的名字
                .deploy();//执行部署
        System.out.println(deploy.getId());
        System.out.println(deploy.getName());
    }

    /** 部署流程, 通过zip格式 */
    @GetMapping("/deploy-zip")
    public void deployByZip(){
        //1.获取processEngine实例
        ProcessEngine processEngine = standaloneProcessEngineConfiguration.buildProcessEngine();
//
        //2.获取repositoryService实例
        RepositoryService repositoryService = processEngine.getRepositoryService();

        //3.转化出ZipInputStream流对象
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("diagram/holidayBPMN.zip");

        //将InputStream转化为ZipInputStream;
        ZipInputStream zipInputStream = new ZipInputStream(is);

        //4.进行部署, holidayBPMN.zip
        Deployment deploy = repositoryService.createDeployment()
//                .addClasspathResource("diagram/holiday.xml")//添加bpmn资源
//                .addClasspathResource("diagram/holiday.png") //添加png资源
                .addZipInputStream(zipInputStream)
                .name("请假流程")//部署的名字
                .deploy();//执行部署
        System.out.println(deploy.getId());
        System.out.println(deploy.getName());
    }

    @GetMapping("/query")
    public void query(){
        //1.得到processEngine
        ProcessEngine processEngine = standaloneProcessEngineConfiguration.buildProcessEngine();

        //2.得到runtimeservice对象
        RepositoryService repositoryService = processEngine.getRepositoryService();

        //3.得到ProcessDefinitionQuery对象,可以认为就是一个查询器
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();

        //4.设置条件，并查询当前定义的所有流程
        //查询条件：流程定义的key=holiday
//        processDefinitionQuery.deploymentId("");
//        processDefinitionQuery.processDefinitionNameLike("");
//        processDefinitionQuery.processDefinitionKey("");
        //返回结果：比如请教流程修改了三次，那么list的size就是3
//        processDefinitionQuery.listPage();
        List<ProcessDefinition> holidayList = processDefinitionQuery.processDefinitionKey("holiday")
                .orderByProcessDefinitionVersion()   //设置排序方式，根据流程定义的版本号进行排序
                .desc()
                .list();

        //5.数量流程定义的信息
        for (ProcessDefinition processDefinition : holidayList) {
            //设置和删除启动的用户或者用户组
//            repositoryService.addCandidateStarterUser(processDefinition.getId(),"userId");
//            repositoryService.addCandidateStarterGroup(processDefinition.getId(),"userGroup");
//            repositoryService.deleteCandidateStarterUser(processDefinition.getId(),"userId");
//            repositoryService.deleteCandidateStarterGroup(processDefinition.getId(),"userGroup");

            System.out.println("流程定义的id："+processDefinition.getId());
            System.out.println("流程定义的名称："+processDefinition.getName());
            System.out.println("流程定义的key："+processDefinition.getKey());
            System.out.println("流程定义的版本号："+processDefinition.getVersion());
            System.out.println("流程定义的部署id："+processDefinition.getDeploymentId());
        }
    }

    /** 删除已经部署的流程定义
     *  影响的表和部署时候的表一致
     *  注意事项：
     *     1、如果流程没走完，会删除失败
     *     2、如果公司层面要强制删除，可以使用repositoryService.deleteDeployment(deploymentId,true);
     *     进行级联删除，此时就会先删除没有完成的流程节点，最后再删除流程定义信息。默认是false
     *
     *     `act_ge_bytearray`： 流程定义文件被删除
     *     `act_re_deployment`：部署表信息被删除
     *      `act_re_procdef`：流程定义信息被删除
     * */
    @GetMapping("/delete")
    public void delete(){
        //1.得到processEngine
        ProcessEngine processEngine = standaloneProcessEngineConfiguration.buildProcessEngine();

        //2.得到runtimeservice对象
        RepositoryService repositoryService = processEngine.getRepositoryService();

        //得到ProcessDefinitionQuery对象,可以认为就是一个查询器
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();

        //4.设置查询参数，查询出定义的流程，流程定义的部署id， id在act_re_deployment
        List<ProcessDefinition> holidayList = processDefinitionQuery.processDefinitionKey("holiday")
                .orderByProcessDefinitionVersion()   //设置排序方式，根据流程定义的版本号进行排序
                .desc()
                .list();

        //5.执行删除操作
        for (ProcessDefinition pd : holidayList) {
            String deploymentId = pd.getDeploymentId();
            repositoryService.deleteDeployment(deploymentId);
//            repositoryService.deleteDeployment(deploymentId,true);
        }

    }

}
