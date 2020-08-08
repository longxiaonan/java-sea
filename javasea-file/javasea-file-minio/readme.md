 上传：
 POST http://localhost:8080/minio/upload
 下载：
 GET http://localhost:8080/minio/download?fileName=a.md
 删除：
 DELETE http://localhost:8080/minio/delete?fileName=a.md

 当下载的文件不存在会抛如下异常：
 ```java
 io.minio.errors.ErrorResponseException: Object does not exist
 	at io.minio.MinioClient.execute(MinioClient.java:1211) ~[minio-7.0.2.jar:7.0.2]
 	at io.minio.MinioClient.execute(MinioClient.java:1236) ~[minio-7.0.2.jar:7.0.2]
 	at io.minio.MinioClient.executeHead(MinioClient.java:1324) ~[minio-7.0.2.jar:7.0.2]
 	at io.minio.MinioClient.executeHead(MinioClient.java:1357) ~[minio-7.0.2.jar:7.0.2]
 	at io.minio.MinioClient.statObject(MinioClient.java:1496) ~[minio-7.0.2.jar:7.0.2]
 	at io.minio.MinioClient.statObject(MinioClient.java:1455) ~[minio-7.0.2.jar:7.0.2]
 	at com.javasea.file.minio.controller.MinioController.download(MinioController.java:44) ~[classes/:na]
 	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[na:1.8.0_181]
 	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62) ~[na:1.8.0_181]
 	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[na:1.8.0_181]
 	at java.lang.reflect.Method.invoke(Method.java:498) ~[na:1.8.0_181]
 	at org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:190) ~[spring-web-5.1.7.RELEASE.jar:5.1.7.RELEASE]
 	at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:138) ~[spring-web-5.1.7.RELEASE.jar:5.1.7.RELEASE]
 	at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:104) ~[spring-webmvc-5.1.7.RELEASE.jar:5.1.7.RELEASE]
 	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:892) ~[spring-webmvc-5.1.7.RELEASE.jar:5.1.7.RELEASE]
 	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:797) ~[spring-webmvc-5.1.7.RELEASE.jar:5.1.7.RELEASE]
 	at org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:87) ~[spring-webmvc-5.1.7.RELEASE.jar:5.1.7.RELEASE]
 	at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:1039) ~[spring-webmvc-5.1.7.RELEASE.jar:5.1.7.RELEASE]
 	at org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:942) ~[spring-webmvc-5.1.7.RELEASE.jar:5.1.7.RELEASE]
 	at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:1005) ~[spring-webmvc-5.1.7.RELEASE.jar:5.1.7.RELEASE]
 	at org.springframework.web.servlet.FrameworkServlet.doGet(FrameworkServlet.java:897) ~[spring-webmvc-5.1.7.RELEASE.jar:5.1.7.RELEASE]
```
