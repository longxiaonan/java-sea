### 1.在父项目springboot-simple2-springsession的pom引入jar
springsession配置参考：
`https://www.cnblogs.com/andyfengzp/p/6434287.html`

```xml
    <!-- spring session 的redis支持 -->
    <dependency>
        <groupId>org.springframework.session</groupId>
        <artifactId>spring-session-data-redis</artifactId>
    </dependency>

```
### 2.通过webserver1项目的接口将session缓存到了redis中：
http://localhost:8081/testSpringSession/longxiaonan

### 3.再通过webserver2的接口获取webserver1中的session，实现服务间的session共享：
http://localhost:8082/testSpringSession

### 4.我们在部署的时候将两个服务部署成负载均衡的方式，当一个无法访问的时候，另外一个服务可以继续提供服务
##### 4.1 通过nginx实现代理测试
结论：需要结合keepalive，否则webserver1或者webserver2挂了了后请求不会自动跳转到在线的服务
> nginx配置见doc的《nginx配置》

##### 4.2 通过haproxy实现代理测试
结论：可以实现，配置文件中根据地址源进行负载均衡。测试时，正常情况下只访问web2server，关掉web2，
web1server响应服务。再启动web2server，服务再次切换到web2server
> haproxy配置见doc的《haproxy配置》，haproxy配置见doc的《haproxy-springsession.cfg》
haproxy安装和配置参考：https://blog.csdn.net/jiankunking/article/details/70184376

>nginx配置解读
在这段配置中：

upstream 表示配置上游服务器
javaboy.org 表示服务器集群的名字，这个可以随意取名字
upstream 里边配置的是一个个的单独服务
weight 表示服务的权重，意味者将有多少比例的请求从 Nginx 上转发到该服务上
location 中的 proxy_pass 表示请求转发的地址， / 表示拦截到所有的请求，转发转发到刚刚配置好的服务集群中
proxy_redirect 表示设置当发生重定向请求时，nginx 自动修正响应头数据（默认是 Tomcat 返回重定向，
此时重定向的地址是 Tomcat 的地址，我们需要将之修改使之成为 Nginx 的地址）。
