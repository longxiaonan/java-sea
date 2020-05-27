

翻译参考：
https://demo.cloud.renren.io/renren-cloud-tenant/#/activiti-model



## springboot整合activiti7

### 添加坐标

### 添加springsecurity安全框架配置信息

### 设置bpmn文件

在resources/processes下添加bpmn文件`holiday4.bpmn`

> activiti7在resources/processes的文件会自动部署

Assignee：直接设置任务执行人

Candidate-user：设置候选用户，格式：wangwu,zhangsan,zhaoliu

Candidate-Groups：特点，不需要知道具体的用户信息，只需要知道组名就可以了

### 使用新的类来实现工作流开发

### 使用新的API实现工作流开发，主要包括

* 流程定义查询
* 启动流程实例
* 任务的查询
* 任务的完成

## 7版本新增的API

### ProcessRuntime

### TaskRuntime