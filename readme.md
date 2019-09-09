# JAVA SEA
![img](https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1568022926280&di=f43efa6166ceae0fbea4e2166a7c7d33&imgtype=0&src=http%3A%2F%2Fimg0.yododo.com.cn%2Ffiles%2Fblog%2F2017-05-14%2F015C056388521DD4FF8080815C03844A.jpg)

![](https://img.shields.io/badge/jdk-1.8-brightgreen.svg?style=plastic)
![](https://img.shields.io/badge/maven-3.5.2-brightgreen.svg?style=plastic)
![](https://img.shields.io/badge/springboot-2.1.5.RELEASE-brightgreen.svg?style=plastic)
## 概述

希望打造一个基于springboot的java全知识栈项目，都是总结好的代码，拿去即用，提高开发效率。因为整个`java`的知识体系很庞大，为了方便整理，所以一些设计到的知识点都整合到了该项目下，项目模块较多，每个知识点都是一个独立的项目（具体看pom的配置）。后期如果代码过大会考虑每个知识点独立成一个项目，该项目作为索引。

项目下很多知识点未完善，正在持续优化中。

## 项目目录结构

子模块下是针对各个知识点的项目。知识点直接的项目无直接依赖。

### ![](https://game.gtimg.cn/images/yxzj/img201606/heroimg/166/166.jpg)javasea-base

javase基础知识测试的demo，具有参考意义。当知识点忘记细节时找的相关目录执行下即可看到结果。

### ![](https://game.gtimg.cn/images/yxzj/img201606/heroimg/121/121.jpg)javasea-cache

java缓存相关的项目， 在service层通过注解`@Cacheable`和`@CacheEvict`实现。

* **javasea-cache-ehcache**

  通过ehcache实现缓存

* **javasea-cache-redis**

  通过redis实现缓存

### ![](https://game.gtimg.cn/images/yxzj/img201606/heroimg/156/156.jpg)javasea-file

文件或者文档相关处理项目

* **javasea-file-archive**

  未完成的文件归档项目，计划是设计一个通用的文件归档中间件，将收集的数据，已对象或json的格式，归档到不同的数据库或者搜索引擎中。例如收集车辆车机的数据到mongodb，elasticsearch和mysql中。

* **javasea-file-download**

  实现文件下载的demo项目。

* **javasea-file-excel**

  实现excel操作的项目，还需要集成和整理如下功能：

  1. poi基本操作的demo
  2. 通过xml格式解析excel的demo
  3. 集成阿里的[easyexcel](<https://github.com/alibaba/easyexcel>)的demo
  4. 测试大数据量的excel导入
  5. 通用的报表导出功能。

- **javasea-file-upload**

  实现文件上传的demo项目。

### ![](https://game.gtimg.cn/images/yxzj/img201606/heroimg/504/504.jpg)javasea-lot

物联网相关测试demo

- **javasea-lot-opc-utgard**

  opc的测试demo，opc虽然是比较旧的工业化控制技术，但是我也把它归属到了物联网中的一部分。

### ![](https://game.gtimg.cn/images/yxzj/img201606/heroimg/187/187.jpg)javasea-mq

消息队列相关的项目

* **javasea-mq-rabbitmq**

  rabbitmq的基本测试

### ![](https://game.gtimg.cn/images/yxzj/img201606/heroimg/149/149.jpg)javasea-nio

* **javasea-nio-netty**

  netty测试的demo，实现了netty服务端和客户端简单通信。

* **javasea-nio-netty2**

  收集github中的netty整合项目，据说能开箱即用，未测试。

### ![](https://game.gtimg.cn/images/yxzj/img201606/heroimg/117/117.jpg)javasea-nosql

springboot整合nosql的项目项目集

* [**javasea-nosql-elasticsearch**](javasea-nosql/javasea-nosql-elasticsearch/readme/readme.md)

  springboot整合es项目。

* **javasea-nosql-mongodb**

  springboot整合mongodb项目

* [**javasea-nosql-redis-distributedlocks**](javasea-nosql/javasea-nosql-redis-distributedlocks/readme-redis-distributedlocks.md)

  redis实现分布式锁

* **javasea-nosql-redis-jedis**

  jedis方式实现redis

* [**javasea-nosql-redis-springdata**](javasea-nosql/javasea-nosql-redis-springdata/readme.md)

  基于springboot和springdata实现redis操作

* [**javasea-nosql-redis-subscription**](javasea-nosql/javasea-nosql-redis-subscription/readme-redis-subscription.md)

  通过redis实现消息订阅

### ![](https://game.gtimg.cn/images/yxzj/img201606/heroimg/511/511.jpg)javasea-orm

springboot集成orm相关框架

* [**javasea-orm-mybatisgenerator-pomplugin**](javasea-orm/javasea-orm-mybatisgenerator-pomplugin/readme-mybatisgenerator.md)

  springboot集成mybatis，且通过pom插件的方式集成mybatis-generator代码生成器。

* [**javasea-orm-mybatisplus**](javasea-orm/javasea-orm-mybatisplus/readme-mybatisplus.md)

  [mybatis-plus](<https://mp.baomidou.com/>)是目前很火的一个mybatis的扩展，极大的简化了对ORM的操作，该项目集成了mybatis-plus，且提供了一些curd的相关示例。

* **javasea-orm-pagehelper**

  springboot集成github的分页插件。

* **javasea-orm-springdata-multidatasource**

  springboot实现多数据源测试demo。

### ![](https://game.gtimg.cn/images/yxzj/img201606/heroimg/507/507.jpg)javasea-patterns

通过java实现的设计模式示例

* [**javasea-patterns-base**](javasea-patterns/javasea-patterns-base/readme-patterns-base.md)

  设计模式基本示例。

* [**javasea-patterns-example**](javasea-patterns/javasea-patterns-example/readme-patterns-example.md)

  收集的设计模式在实际项目使用的代码

### ![](https://game.gtimg.cn/images/yxzj/img201606/heroimg/510/510.jpg)javasea-rbac

java后台的权限模块相关项目，如shiro，springdata，sso，oauth2等相关技术的demo。

* [**javasea-rbac-jwtsecurity**](javasea-rbac/javasea-rbac-jwtsecurity/readme-security.md)

  springboot集成spring-security和jwt

* [**javasea-rbac-jwtsecurity-sso**](javasea-rbac/javasea-rbac-jwtsecurity-sso/readme-jwtsecurity-sso.md)

  springboot集成spring-security，jwt和sso

* [**javasea-rbac-security**](javasea-rbac/javasea-rbac-security/readme.md)

  [imooc网的security课程](<https://coding.imooc.com/learn/list/134.html>)的代码实践

* [**javasea-rbac-shiro**](javasea-rbac/javasea-rbac-shiro/rabcShiroReadme.md)

  springboot整合shiro

### ![](https://game.gtimg.cn/images/yxzj/img201606/heroimg/154/154.jpg)javasea-scene

在一些特定的场景下需要的一些功能

* [**javasea-scene-repeatsubmit**](javasea-scene/javasea-scene-repeatsubmit/readme-repeatsubmit.md)

  请求重复提交的场景，代码遗漏，需要补充

* [**javasea-scene-simplemail**](javasea-scene/javasea-scene-simplemail/readme-simplemail.md)

  邮件发送demo

### ![](https://game.gtimg.cn/images/yxzj/img201606/heroimg/118/118.jpg)javasea-schedule

 任务调度相关项目，将整合quartz，XXL-Job, elastic-job

* [**javasea-schedule-quartz**](javasea-schedule/javasea-schedule-quartz/readme-schedule-quartz.md)

### ![](https://game.gtimg.cn/images/yxzj/img201606/heroimg/115/115.jpg)[javasea-volcano](javasea-volcano/zhirui-lmwy2-readme.md)

该项目是基于springboot和SSM后台的开发脚手架，已经整合了工具类，通过mybatis-plus实现ORM操作。定义了异常出来，参数校验等示例。

* **javasea-volcano-base**

  脚手架项目测试的demo，使用该脚手架时，参考该demo配置即可。

* **javasea-volcano-common**

  脚手架项目的common项目，整合了工具类，swagger等常用工具。

### ![](https://game.gtimg.cn/images/yxzj/img201606/heroimg/190/190.jpg)javasea-web

基于springboot和spring在web开发时常用的一些功能

* **javasea-web-aspect**

  面向切面编程AOP测试等

* **javasea-web-config**

  springboot配置加载demo

* [**javasea-web-devtools**](javasea-web/javasea-web-devtools/devtoolsReadme.md)

  springboot热部署demo

* [**javasea-web-junit**](javasea-web/javasea-web-junit/readme-web-junit.md)

  springboot通过junit单元测试demo

* [**javasea-web-log**](javasea-web/javasea-web-log/web-log-readme.md)

  springboot实现log的demo，且能将log保存到mysql中。

* **javasea-web-rxjava**

  springboot整合rxjava，实现异步编程的demo

* [**javasea-web-schedule**](javasea-web/javasea-web-schedule/readme-web-schedule.md)

  springboot实现的各种job调度的demo。

* [**javasea-web-springsession**](javasea-web/javasea-web-springsession/readme.md)

  两个不同web项目，通过spring session实现session同步

* **javasea-web-template**

  基于springboot在web开发中使用模板引擎demo。

* **javasea-web-threadpool**

  基于springboot实现不同的线程池

* **javasea-web-webbase**

  web开发的基本整合， 请参考更加全面的[javasea-volcano](#javasea-volcano)项目

* **javasea-web-webflux**

  springboot整合webflux实现异步编程demo， 待完善。

### ![](https://game.gtimg.cn/images/yxzj/img201606/heroimg/191/191.jpg)javasea-workflow

* **javasea-workflow-activiti**

  activiti工作流项目



## 参与项目

项目很多地方未完善，我一个人的精力时间有限，如果你愿意跟我一起来完善，我将非常荣幸。

#### 命名规范
项目名，子项目名，包名，启动类名，readme名称规范：
![](images/2019-06-12-11-01-39.png)

#### 创建子项目
选择相应的项目，通过新建模块的方式创建子项目。二级项目和三级项目都是该方式进行创建
![](images/2019-06-12-11-11-29.png)
#### pom配置规范
1. 请将添加的依赖分类整理放好，添加相关的备注。
![](images/2019-06-12-11-10-39.png)
2. 将三级项目的pom.xml中的<parent>修改为springboot的parent，减少对二级和一级项目的依赖。使之就算单独拷贝出去也能独立运行。
![](images/2019-06-12-11-15-48.png)

### 相关说明
#### fork后如何更新项目问题
参考：https://blog.csdn.net/libaineu2004/article/details/83239778

#### 项目太多卡顿问题
在idea中关闭不启用(加载)的module。<br/>
<img src="images/2019-06-15-22-22-51.png" width = 50% height = 50% /><br/>
将要加载项目移到右边即可。<br/>
<img src="images/2019-06-15-22-28-29.png" width = 80% height = 80% /><br/>
每个节点中的子项目都可以独立运行。<br/>
<img src="images/2019-06-15-22-28-57.png" width = 50% height = 50% /><br/>
