# Spring 最佳实践

本项目用于展示个人在使用Spring开发的过程中总结的最佳实践

* 统一异常处理
* 使用 Valid 注解做参数校验，并统一处理校验不通过的异常
* 使用 lombok 简化开发
* 使用 Swagger 自动化 API 文档
* REST 接口统一返回 ResultBean\<T\>
* 通过构造器注入
* 单元测试

# 启动方式

下载源码：

```shell
git clone https://github.com/dadiyang/spring-best-practice.git
cd spring-best-practice
```

然后直接启动：

`mvn spring-boot:run`

启动之后浏览器打开 http://localhost:8082/swagger-ui.html#/ 可以查看 Swagger 文档