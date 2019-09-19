# Volcano

![](https://img.shields.io/badge/JDK-1.8-brightgreen.svg?style=plastic)
![](https://img.shields.io/badge/SpringBoot-2.1.5.RELEASE-brightgreen.svg?style=plastic)
![](https://img.shields.io/badge/MySQL-5.6-brightgreen.svg?style=plastic)
![](https://img.shields.io/badge/Redis-3.2-brightgreen.svg?style=plastic)
![](https://img.shields.io/badge/MybatisPlus-3.2.0-brightgreen.svg?style=plastic)
![](https://img.shields.io/badge/JWT-0.9.1-brightgreen.svg?style=plastic)
![](https://img.shields.io/badge/Swagger-2.9.2-brightgreen.svg?style=plastic)
[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)

## 概述

Volcano是一个基于springboot后台开发简易脚手架。能实现当作一个单独项目或者作为微服务的一个节点进行快速开发和部署。

## 项目结构

javasea-volcano 父项目, 用于springcloud和springboot的版本管控, maven插件, 仓库统一管控等.

|--javasea-volcano-base:  测试类和相关业务配置， 依赖`common`项目中的组件，开发的时候在该项目的基础上添                            加添加业务代码即可。

|--javasea-volcano-common:常用组件和工具类

## 项目环境

中间件 | 版本 |  备注
-|-|-
JDK | 1.8+ | JDK1.8及以上 |
MySQL | 5.6+ | 5.6及以上 |
Redis | 3.2+ |  |

## 基本功能

参考包`com.zhirui.lmwy.wms.demo`下的配置

### 参数校验

#### JSR校验

> `spring-boot-starter-web`已经默认集成了JSR303校验，只需要直接使用注解校验即可。

参考测试类： com.zhirui.lmwy.wms.demo.web.controller.TestCheckParamController

#### 手动校验

`Assert`类定义了不满足条件后快速断言的方式，可以在校验参数中使用。

还可以采用Spring的Assert进行校验

```java
//第一个参数为false则抛出IllegalArgumentException异常
Assert.isTrue(concurrentConsumers > 0, "'concurrentConsumers' value must be at least 1 (one)");
Assert.isTrue(!this.exclusive || concurrentConsumers == 1,"When the consumer is exclusive, the concurrency must be 1");
```

也可以采用Optional进行校验

```java
ZOrder order = this.getOrderByOrderNum(orderNum);
Optional.ofNullable(order).filter(o -> {
    return null != o && (0 == o.getStatus() || 3 == o.getStatus() || 9 == o.getStatus());
}).orElseThrow(() -> new ParamException("获取数据异常,订单号有误或者订单状态异常!"));
```

> 还可以通过guava的Preconditions类来进行参数检查。有需要请自行百度。

### 参数转换

参考测试类：TestDateConverterAndJson

#### URL方式传值到后端转换

> URL传参到后端包括 如下三种方式传参：
>
> ```java
> @GetMapping("testDateConverter")
> public Student testDateConverter(@RequestParam Student student){...}
>
> @GetMapping("testDateConverter")
> public Student testDateConverter(Student student){...}
>
> @GetMapping("testDateConverter/{id}")
> public Student testDateConverter(@PathVariable Integer id){...}
> ```

`com.zhirui.lmwy.common.converter`包下定义了很多转换类：

* StringToDateConverter

  如果实体属性是Date，通过该转换器将String转Date类型。效果和日期属性上的注解`@DatetimeFormat`相同。但是启用该转换器的时候，实体属性上的注解`@DatetimeFormat`不再生效。

* StringToDoubleConverter

  如果实体属性是Double，通过该转换器将String转Double类型。

* StringToIntegerConverter

  如果实体属性是Integer，通过该转换器将String转Integer类型。

#### 请求体方式传值到后端转换

> 请求体方式传参到后端 包括如下方式：
>
> ```java
> @PostMapping("testDateConverter2")
> public Student testDateConverter2(@RequestBody Student student){...}
> ```

`com.zhirui.lmwy.common.json.jackson`包下定义了JSON的序列化和反序列化方式：

* deserializer 包下的序列化类

  在@RequestBody接参时候，会调用该包中的`序列化类`将JSON转换成实体接参。

* serializer 包下的序列化类

  用于后端传值给前端。

#### 后端传值给前端

`com.zhirui.lmwy.common.json.jackson.serializer`包下的定义了序列化类 。

在Controller类上添加注解@ResponseBody或者@RestController，那么后端传值给前端是JSON方式。

如果是返回值是对象或者集合，会用序列化类进行参数类型转换。

如下列子中，Student中的两个属性日期会转换成serializer 定义的格式“yyyy-MM-dd HH:mm:ss”。
> `序列化类``JacksonDateSerializer`的作用相当于在属性上添加了`@JsonFormat(pattern="yyyy-MM-dd")`，实现将Date类型转换成String，但是添加`JacksonDateSerializer`后 `@JsonFormat(pattern="yyyy-MM-dd")`失效。
```java
    @PostMapping("testDateConverter2")
    public Student testDateConverter2(@RequestBody Student student){
        System.out.println(student);
        Student s = new Student();
        s.setBirth(new Date());
        s.setCreateTime(LocalDateTime.now());
        return s;
    }
```



### 异常处理

在`com.zhirui.lmwy.common.exception.impl`中定义了三大类异常：

```
BusinessException： 通用业务异常
ParamException：参数校验异常
AuthenticationException：认证失败异常
```

`Exceptions`类定义了快捷抛出异常的一些通用方法，在需要抛出异常时请不要去`throw new XXXException()`，而是用`Exceptions`类的方法进行操作。

已经定义了全局异常处理器`GlobalExceptionHandler`对各种异常可以进行处理，请不要在controller和service中`try..catch`。

### 返回值处理

controller的返回前端使用`ResultModel`类进行封装，里面有`code`，`msg`，`data`等字段。

各种场景下的ResultModel返回：

```java
    //插入后返回方式， msg：插入成功；插入失败
    public ResultModel resultInsert(){
        boolean flag = false;
        return ResultModel.resultInsert(flag);
    }

    //更新后返回方式， msg：更新成功；更新失败
    public ResultModel resultUpdate(){
        boolean flag = false;
        return ResultModel.resultUpdate(flag);
    }

    //删除后返回方式， msg：删除成功；删除失败
    public ResultModel resultDelete(){
        boolean flag = false;
        return ResultModel.resultDelete(flag);
    }

    //删除后返回方式， msg：操作成功；操作失败
    public ResultModel result(){
        boolean flag = false;
        return ResultModel.result(flag);
    }

    //认证失败返回方式， msg：认证信息异常
    public ResultModel errorTokenMsg(){
        boolean flag = false;
        //如果msg参数为null，那么是默认的msg：认证信息异常
        return ResultModel.errorTokenMsg(null);
    }
```

> 推荐优先上面的放回方式，如果不能满足，还可以使用如下通用的`失败`、`成功`的返回方式：
>
> ```java
> ResultModel.error();  //失败返回方式
> ResultModel.ok();       //成功返回方式
> ```

### AOP 日志输出

1. 添加坐标

```xml
<!-- AOP -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>
<!-- console彩色日志 -->
<dependency>
    <groupId>org.fusesource.jansi</groupId>
    <artifactId>jansi</artifactId>
    <version>1.18</version>
</dependency>
```

2. 定义AOP类实现彩色日志输出

参考：`com.zhirui.lmwy.common.aop.LogAop`

### 集成 丝袜哥

在common项目中集成了swagger

1. pom中添加注解

``` xml
        <!-- swagger start -->
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger2</artifactId>
            <version>2.9.2</version>
        </dependency>
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger-ui</artifactId>
            <version>2.9.2</version>
        </dependency>
        <!-- swagger end -->
```

2. 添加配置

   两个配置类：

   com.zhirui.lmwy.common.swagger.SwaggerConfiguration

   com.zhirui.lmwy.common.swagger.SwaggerProperties

3. 设置自定义显示参数

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

4. 在controller和model中使用swagger

   controller类中使用参考：`com.zhirui.lmwy.wms.demo.web.controller.TestCurdController`

   model类中使用参考：`com.zhirui.lmwy.wms.demo.web.entity.User`

5. 通过swagger进行http请求

   * <http://localhost:8080/swagger-ui.html#/>

     丝袜哥默认的访问方式

   * <http://localhost:8080/docs>

     通过controller 重定向后的访问方式

### 集成 mybatis-plus（下文中称为MP）

> ORM框架使用[mybatis-plus](https://mp.baomidou.com/)，简便了CURD操作

1. 添加pom

   ```java
           <!-- mybatis-plus -->
           <dependency>
               <groupId>com.baomidou</groupId>
               <artifactId>mybatis-plus-boot-starter</artifactId>
               <version>3.2.0</version>
           </dependency>
           <!-- 代码生成器  start -->
           <dependency>
               <groupId>com.baomidou</groupId>
               <artifactId>mybatis-plus-generator</artifactId>
               <version>3.2.0</version>
           </dependency>
           <!-- 代码生成器需要的引擎模板 -->
           <dependency>
               <groupId>org.apache.velocity</groupId>
               <artifactId>velocity-engine-core</artifactId>
               <version>2.0</version>
           </dependency>
           <!-- 代码生成器  end -->
   ```

2. 在yml中进行配置

   ```yml
   mybatis-plus:
     mapper-locations: classpath:mapper/**/*.xml
     global-config:
       db-config:
         id-type: AUTO  #主键自增长
   ```

3. 添加配置类配置

   > 下列代码中使用了租户模式，如果只是单纯需要添加分页插件，只需要如下方式即可：
   >
   > ```java
   > @Bean
   > public PaginationInterceptor paginationInterceptor() {
   >     PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
   >     return paginationInterceptor;
   > }
   > ```

   ```java
   /** 分页插件: 实现物理分页 */
   @Bean
   public PaginationInterceptor paginationInterceptor() {
       PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
       ArrayList<ISqlParser> iSqlParsers = new ArrayList<>();
       TenantSqlParser tenantSqlParser = new TenantSqlParser();
       tenantSqlParser.setTenantHandler(new TenantHandler() {
           @Override
           public Expression getTenantId(boolean where) {
               return new StringValue(tenantId);
           }

           @Override
           public String getTenantIdColumn() {
               //指定表中的租户列
               return "tenant_id";
           }
           @Override
           public boolean doTableFilter(String tableName) {
               return false;
           }
       });
       iSqlParsers.add(tenantSqlParser);
       paginationInterceptor.setSqlParserList(iSqlParsers);
       return paginationInterceptor;
   }
   ```

#### 通过MP进行CRUD操作

参考测试类和[官网](https://mp.baomidou.com/)：`com.zhirui.lmwy.wms.demo.web.controller.TestCurdController`

#### 代码生成器

采用了MP的代码生成器，实现了两个代码生成器。
> 在`javasea-volcano-base`项目的`src/test/java`目录的 com.zhirui.lmwy.wms包下，按照注释修改为自己需要的配置运行即可。看个人习惯，推荐用`PrimaryCodeGenerator `。

- SencondCodeGenerator

  通过数据库表生成基本的entity，mapper，controller和service类等基本类。

- PrimaryCodeGenerator

  在 `SencondCodeGenerator` 功能的基础上，controller，entity中生成swagger的注解。controller、service生成常用的crud方法。

### 集成redis

1. 添加pom坐标

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>

```

2. yml中进行配置

```yml
spring:
    redis:
      host: 127.0.0.1
      port: 6379
      password: zhirui888
      timeout: 2000
      database: 6
      lettuce:
        pool:
          max-active: 8
          max-wait: 1
          max-idle: 8
          min-idle: 0
```

3. 配置类中进行配置

   * 定义redis的RedisTemplate

     > 详见：com.zhirui.lmwy.common.redis.RedisTemplateConfig

   * 开启springcache

     >  详见：com.zhirui.lmwy.common.redis.RedisCacheConfig

   * redis工具类

     > 详见：com.zhirui.lmwy.common.redis.RedisUtils

### 集成 JWT

#### 添加jwt坐标

```xml
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt</artifactId>
    <version>0.9.1</version>
    <scope>compile</scope>
</dependency>
```

#### yml中配置jwt

```yaml
custom:
    jwt:
      header: token
      secret: 666666          #密码，用于生成签名
      issuer: volcano         #签发人
      subject: volcano-jwt    #主题
      audience: web           #签发的目标
      expire-minutes: 20      #过期时间
    interceptor:
      jwt:
        exclude:
          path: /swagger-resources/**,/api-docs/**,/v2/api-docs/**,/login,/verificationCode,/doc/**,/error/**,/docs,/test/**
      permission:
        exclude:
          path: /swagger-resources/**,/api-docs/**,/v2/api-docs/**,/adminLogin,/sysLogin,/login.html,/verificationCode,/doc/**,/error/**,/docs
      token-timeout:
        exclude:
          path: /swagger-resources/**,/api-docs/**,/v2/api-docs/**,/docs
```



#### 添加登陆接口

登陆后会将token信息保存到redis

> 详见登陆controller：com.zhirui.lmwy.wms.security.controller.LoginController

#### 添加jwt拦截器

拦截到请求后会进行校验，校验方式如下：

```java
// 验证token是否有效
Jws<Claims> jws = JwtUtil.verify(token);
```

token未过期且合法的才能校验通过，否则抛出401异常。

> 详见jwt拦截器：com.zhirui.lmwy.wms.security.interceptor.JwtInterceptor

#### 在swagger进行token测试

1. 执行登陆操作，token见返回值中

   ![1568710656833](media/1568710656833.png)

   返回值为：

   ```json
   {
     "code": 200,
     "msg": "操作成功",
     "data": {
       "loginSysUser": {
         "id": "1",
         "userName": "admin"
       },
       "token": "eyJjdHkiOiJjdHkiLCJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ2b2xjYW5vLWp3dCIsImF1ZCI6IndlYiIsImlzcyI6InZvbGNhbm8iLCJleHAiOjE1Njg3MTI0MDcsImlhdCI6MTU2ODcxMDYwNywianRpIjoiNDM4NjMwMDAwOTlmNDFkNDk1Y2FlOWVkNWUzNDZhOTgifQ.wVZOx-J2knqUxdnRjRXZqr2nf1S-Qwmap2-0nGXJDXM"
     },
     "time": "2019-09-17 16:56:51"
   }
   ```

2. 点击 `Authorize`按钮

   ![1568710795850](media/1568710795850.png)

3. 将token设置到value中，后面丝袜哥所有的请求都会带一个叫做“Authorization”的请求头。

   在jwt拦截器`JwtInterceptor`中会获取该请求头进行校验。

   ![1568710877266](media/1568710877266.png)

## 项目启动和部署

### 单独作为项目使用

`javasea-volcano`默认是一个独立的springboot项目，可以直接启动`javasea-volcano-base`项目，在`base`项目的基础上，直接编写业务代码即可。

### 集成到现有的springcloud中

默认是独立项目，但是也可以轻松的集成到现有的springcloud环境中，只需要放开部分注释即可。

1. 启用bootstrap.yml的配置(放开注释)

   > ```yml
   > #spring:
   > #  cloud:
   > #    config:
   > #      uri: ${WMS_CONFIG_SERVER_URL}
   > #      name: zhirui-lmwy2-wms${WMS_DEVELOPER_NAME:}
   > #      profile: ${config.profile:dev}
   > #
   > ```

2. base项目的pom中开启对应eureka client的坐标，启动类`WmsApplication`通过注解`@EnableDiscoveryClient`启用eureka client。

   ```xml
   <!-- springboot 1.X -->
   <!--<dependency>-->
      <!--<groupId>org.springframework.cloud</groupId>-->
      <!--<artifactId>spring-cloud-starter-eureka</artifactId>-->
   <!--</dependency>-->
   <!-- springboot 2.X -->
   <!--<dependency>-->
      <!--<groupId>org.springframework.cloud</groupId>-->
      <!--<artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>-->
   <!--</dependency>-->
   ```

3. IDE中配置vm参数，使用已经已经存在的`eureka注册中心`和`配置中心`

   > 在IDE中配置VM参数只能是作为测试，如果需要部署后生效，那么需要配置到服务器的环境变量或者在启动的java  -jar后添加配置。之后我会添加关于服务部署的文章。

   ```shell
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



