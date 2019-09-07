[TOC]

### 概述

Volcano是一个基于springboot后台开发简易脚手架。能实现当作一个单独项目或者作为微服务的一个节点进行快速开发和部署。

### 项目结构

javasea-volcano 父类, 用于springcloud和springboot的版本管控, maven插件, 仓库统一管控等. 
|--javasea-volcano-base:  测试类和相关配置
|--javasea-volcano-common:常用组件和工具类

### 基本功能

参考包`com.zhirui.lmwy.wms.demo`下的配置

#### 参数校验

##### JSR校验

参考： com.zhirui.lmwy.wms.demo.web.controller.TestCheckParamController

##### 手动校验

采用Spring的Assert进行校验

```java
//第一个参数为false则抛出IllegalArgumentException异常
Assert.isTrue(concurrentConsumers > 0, "'concurrentConsumers' value must be at least 1 (one)");
Assert.isTrue(!this.exclusive || concurrentConsumers == 1,"When the consumer is exclusive, the concurrency must be 1");
```

复杂校验通过Optional

```java
ZOrder order = this.getOrderByOrderNum(orderNum);
Optional.ofNullable(order).filter(o -> {
    return null != o && (0 == o.getStatus() || 3 == o.getStatus() || 9 == o.getStatus());
}).orElseThrow(() -> new ParamException("获取数据异常,订单号有误或者订单状态异常!"));
```



#### 异常处理

在`com.zhirui.lmwy.common.exception.impl`中定义了三大类异常：

```
BusinessException： 通用业务异常
ParamException：参数校验异常
AuthenticationException：认证失败异常
```

`Exceptions`类定义了快捷抛出异常的一些通用方法，在需要抛出异常时请不要去`throw new XXXException()`，而是用`Exceptions`类的方法进行操作。

已经定义了全局异常处理器`GlobalExceptionHandler`对各种异常可以进行处理，请不要在controller和service中`try..catch`。

#### 返回值处理

controller的返回前端使用`ResultModel`类进行封装，里面有`code`，`msg`，`data`等字段。

#### 配置swagger

在common项目中集成了swagger

在base项目的`application.yaml`中添加如下配置进行设置：

```java
swagger:
  #  open: true              #是否开启swagger,在生产环境下需要关闭
  protocol: http          #协议，http或https
  base-package: com.zhirui.lmwy.wms   #一定要写对，会在这个路径下扫描controller定义
  title: volcano-base项目
  version: 1.0
  description: volcano-base项目丝袜哥测试
```

controller类中使用参考：`com.zhirui.lmwy.wms.demo.web.controller.TestCurdController`

model类中使用参考：`com.zhirui.lmwy.wms.demo.web.entity.User`

#### 使用mybatis-plus

> ORM框架使用mybatis-plus，简便了CURD操作

##### 代码生成器

代码生成器在src/test/java目录的 com.zhirui.lmwy.wms.MyGeneratorTest，按照注释修改为自己需要的配置。

##### curd操作

参考：`com.zhirui.lmwy.wms.demo.web.controller.TestCurdController`



### 代码生成器

通过代码生成器生成mapper，entity，service和controller。代码生成器在`src/test/java`的`com.zhirui.lmwy.wms.MyGeneratorTest`下。

### 项目启动和部署

#### 数据库连接

```yaml
jdbc:mysql://192.168.1.230:3306/lmwy2
```



### 集成到现有的springcloud中

#### IDE中配置vm参数，使用已经已经存在的`eureka注册中心`和`配置中心`

> 在IDE中配置VM参数只能是作为测试，如果需要部署后生效，那么需要配置到服务器的环境变量或者在启动的java -jar后添加配置。具体参考我另外的关于服务部署的文章。

```ruby
-Dmultipart-location=D:/wms/temp
-DDiskLocation=D:/wms/
-DWMS_CONFIG_SERVER_URL=http://192.168.1.230:8861
-DWMS_EUREKA_SERVER_URL=http://192.168.1.230:8090/eureka/
-DWMS_DEVELOPER_NAME=-longxiaonan
-Dconfig.profile=dev
```

### Maven方式打包

```xml
mvn clean package -Dmaven.test.skip=true
```



