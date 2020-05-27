访问：
http://localhost:8080/


文件上传大小限制：
Spring Boot1.4版本后配置更改为:

spring.http.multipart.maxFileSize = 10Mb
spring.http.multipart.maxRequestSize=100Mb
Spring Boot2.0之后的版本配置修改为:

spring.servlet.multipart.max-file-size = 10MB
spring.servlet.multipart.max-request-size=100MB
