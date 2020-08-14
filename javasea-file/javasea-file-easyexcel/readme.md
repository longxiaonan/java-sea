包结构：

controller：

​	测试类入口, 包括导入导出测试，导出合并测试

entity：

​	测试时需要的实体类

relase：

​	将导入导出简单封装

helpful：

​	参考用的工具类

converter：

    导出导入时，进行类型转换，比如：1和男，2和女 相互转换

测试访问：
http://localhost:8080/

文件上传大小限制：

Spring Boot1.4版本后配置更改为:

spring.http.multipart.maxFileSize = 10Mb

spring.http.multipart.maxRequestSize=100Mb

Spring Boot2.0之后的版本配置修改为:

spring.servlet.multipart.max-file-size = 10MB

spring.servlet.multipart.max-request-size=100MB
