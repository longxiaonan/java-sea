### 1.在父项目springboot-simple2-springsession的pom引入jar
```xml
    <!-- spring session 的redis支持 -->
    <dependency>
        <groupId>org.springframework.session</groupId>
        <artifactId>spring-session-data-redis</artifactId>
    </dependency>

```
### 2.通过webserver1项目的接口将session缓存到了redis中：
http://localhost:8081/testSpringSession/longxiaonan

### 3.再通过webserver2的接口获取webserver1中的session，实现session共享：
http://localhost:8082/testSpringSession

### 4.我们在部署的时候将两个服务部署成负载均衡的方式，当一个无法访问的时候，另外一个服务可以继续提供服务
##### 4.1 通过nginx实现代理测试
结论：需要结合keepalive，否则webserver1或者webserver2挂了了后请求不会自动跳转到在线的服务
> nginx配置见doc的《nginx配置》  

##### 4.2 通过haproxy实现代理测试
结论：可以实现，配置文件中根据地址源进行负载均衡。测试时，正常情况下只访问web2server，关掉web2，
web1server响应服务。再启动web2server，服务再次切换到web2server
> haproxy配置见doc的《haproxy配置》，haproxy配置见doc的《haproxy-springsession.cfg》

