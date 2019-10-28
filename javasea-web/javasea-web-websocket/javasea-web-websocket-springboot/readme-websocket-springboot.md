# websoket原理和实战

## 概述

项目地址：https://github.com/longxiaonan/java-sea/tree/master/javasea-web/javasea-web-websocket/javasea-web-websocket-springboot

### 项目包结构
两个包：
com.javasea.web.websocket.springb.websocket
使用实现`WebSocketConfigurer`接口的方式实现

com.javasea.web.websocket.springb.websocket2
通过注解`@ServerEndpoint`方式实现

### 场景引用

场景：页面需要实时显示被分配的任务，页面需要实时显示在线人数。

思考：像这样的消息功能怎么实现？ 如果网页不刷新，服务端有新消息如何推送到浏览器？  
解决方案，采用轮询的方式。即：通过js不断的请求服务器，查看是否有新数据，如果有，就获取到新数据。
这种解决方法是否存在问题呢？  
当然是有的，如果服务端一直没有新的数据，那么js也是需要一直的轮询查询数据，这就是一种资源的浪费。
那么，有没有更好的解决方案？ 有！那就是采用WebSocket技术来解决。  

### 什么是WebSocket？

WebSocket 是HTML5一种新的协议。它实现了浏览器与服务器全双工通信(full-duplex)。一开始的握手需要借助HTTP请求完成。 WebSocket是真正实现了全双工通信的服务器向客户端推的互联网技术。 它是一种在单个TCP连接上进行全双工通讯协议。Websocket通信协议与2011年倍IETF定为标准RFC 6455，Websocket API被W3C定为标准。

> 什么叫做全双工和半双工？
>
> 比如对讲机，说话的时候就听不到对方说话，那么就是半双工。
>
> 我们打电话的时候说话的同时也能听到对方说话，就是全双工。

### http与websocket的区别

http协议是短连接，因为请求之后，都会关闭连接，下次重新请求数据，需要再次打开链接。

![](https://user-gold-cdn.xitu.io/2019/10/27/16e0c06d56ad2298?w=609&h=410&f=png&s=175124)

WebSocket协议是一种长链接，只需要通过一次请求来初始化链接，然后所有的请求和响应都是通过这个TCP链接进行通讯。

![](https://user-gold-cdn.xitu.io/2019/10/27/16e0c07f5539c17e?w=558&h=533&f=png&s=185337)

### 浏览器支持情况

查看：https://caniuse.com/#search=websocket

![](https://user-gold-cdn.xitu.io/2019/10/27/16e0c0880f9d67a1?w=1006&h=357&f=png&s=76753)

> 服务器支持情况：Tomcat 7.0.47+以上才支持。

## 快速入门

### 创建项目

### 配置pom.xml

- 集成javaee

  ```xml
   <dependency>
       <groupId>javax</groupId>
       <artifactId>javaee-api</artifactId>
       <version>7.0</version>
       <scope>provided</scope>
  </dependency>
  ```

- 配置tomcat插件

  ```xml
   <plugin>
       <groupId>org.apache.tomcat.maven</groupId>
       <artifactId>tomcat7-maven-plugin</artifactId>
       <version>2.2</version>
       <configuration>
           <port>8082</port>
           <path>/</path>
       </configuration>
  </plugin>
  ```

  之后`启动服务`只需要在maven中直接运行即可。

![](https://user-gold-cdn.xitu.io/2019/10/27/16e0c30580268d37?w=593&h=665&f=png&s=81792)

pom的详细配置如下：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.iee</groupId>
    <artifactId>javasea-web-websoecket-quickstart</artifactId>
    <version>1.0-SNAPSHOT</version>
    <packaging>war</packaging>
    <dependencies>
        <!-- https://mvnrepository.com/artifact/javax/javaee-api -->
        <dependency>
            <groupId>javax</groupId>
            <artifactId>javaee-api</artifactId>
            <version>7.0</version>
            <!--<scope>provided</scope>-->
        </dependency>
    </dependencies>
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
            <!--maven编译插件, 指定jdk为1.8 -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.7.0</version>
                <configuration>
                    <source>1.8</source>
                    <target>1.8</target>
                    <!-- 使用jdk进行编译 -->
                    <fork>true</fork>
                    <encoding>UTF-8</encoding>
                </configuration>
            </plugin>
            <!-- 配置Tomcat插件 -->
            <plugin>
                <groupId>org.apache.tomcat.maven</groupId>
                <artifactId>tomcat7-maven-plugin</artifactId>
                <version>2.2</version>
                <configuration>
                    <port>8082</port>
                    <path>/</path>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```

### websocket的相关注解说明

- `@ServerEndpoint("/websocket/{uid}")`
  申明这是一个websocket服务
  需要指定访问该服务的地址，在地址中可以指定参数，需要通过{}进行占位

- `@OnOpen`
  用法：public void onOpen(Session session, @PathParam("uid") String uid) throws
  IOException{}
  该方法将在建立连接后执行，会传入session对象，就是客户端与服务端建立的长连接通道
  通过@PathParam获取url申明中的参数

- `@OnClose`
  用法：public void onClose() {}
  该方法是在连接关闭后执行

- `@OnMessage`
  用法：public void onMessage(String message, Session session) throws IOException {}

  客户端消息到来时调用，包含会话Session，根据消息的形式，如果是文本消息，传入String类型参数或者Reader，如果是二进制消息，传入byte[]类型参数或者InputStream。

  message：发来的消息数据
  session：会话对象（也是通道）
  发送消息到客户端
  用法：session.getBasicRemote().sendText("你好");
  通过session进行发送。

### 实现websocket服务

```java
@ServerEndpoint("/websocket/{uid}")
public class MyWebSocket {
  @OnOpen
  public void onOpen(Session session, @PathParam("uid") String uid) throws
IOException {
    // 连接成功
    session.getBasicRemote().sendText(uid + "，你好，欢迎连接WebSocket！");
 }
  @OnClose
  public void onClose() {
    System.out.println(this + "关闭连接");
 }
  @OnMessage
  public void onMessage(String message, Session session) throws IOException {
    System.out.println("接收到消息：" + message);
    session.getBasicRemote().sendText("消息已收到.");
 }
  @OnError
  public void onError(Session session, Throwable error) {
    System.out.println("发生错误");
    error.printStackTrace();
 }
}
```

maven中启动tomcat：

```
mv tomcat7:run
```

> 也可以用上文中在IDE中直接启动。

### 测试

一共有三种测试方式，直接js脚本方式、chrome插件方式或者通过在线工具进行测试：

- 直接js脚本方式，直接用如下代码进行测试：

  ```javascript
  var socket;
      if(typeof(WebSocket) == "undefined") {
          console.log("您的浏览器不支持WebSocket");
      }else{
          console.log("您的浏览器支持WebSocket");
          	//实现化WebSocket对象，指定要连接的服务器地址与端口  建立连接
              socket = new WebSocket("ws://localhost:8080/websocket2/22");
              //打开事件
              socket.onopen = function() {
                  console.log("Socket 已打开");
                  //socket.send("这是来自客户端的消息" + location.href + new Date());
              };
              //获得消息事件
              socket.onmessage = function(msg) {
                  console.log(msg.data);
                  //发现消息进入    开始处理前端触发逻辑
              };
              //关闭事件
              socket.onclose = function() {
                  console.log("Socket已关闭");
              };
              //发生了错误事件
              socket.onerror = function() {
                  alert("Socket发生了错误");
                  //此时可以尝试刷新页面
              }
              //离开页面时，关闭socket
              //jquery1.8中已经被废弃，3.0中已经移除
              // $(window).unload(function(){
              //     socket.close();
              //});
      }

  ```

  浏览器随便打开一个网页，然后粘贴到console下，回车即可

  ![](https://user-gold-cdn.xitu.io/2019/10/27/16e0ce271b545006?w=822&h=671&f=png&s=69291)

- chrome插件方式，需要安装chrome插件，Simple WebSocket Client：
  https://chrome.google.com/webstore/detail/simple-websocket-client/pfdhoblngboilpfeibdedpjgfnlcodoo

![](https://user-gold-cdn.xitu.io/2019/10/27/16e0c44756d7505c?w=643&h=470&f=png&s=52641)

- 在线工具进行测试（推荐）：<http://www.websocket-test.com/>

  > 我一直测试失败，还没找到原因。下文整合springboot的测试成功。

  ![](https://user-gold-cdn.xitu.io/2019/10/27/16e0c6e93bfdaa60?w=1014&h=844&f=png&s=190820)

### 编写js客户端

在webapp下编写两个html文件

![](https://user-gold-cdn.xitu.io/2019/10/27/16e0c701b1f5f226?w=390&h=220&f=png&s=13158)

- `websocket.html`内容如下

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
      <title>Title</title>
</head>
<body>
<script>
    const socket = new WebSocket("ws://localhost:8082/websocket/1");
    // 连接建立时触发
    socket.onopen = (ws) => {
        console.log("建立连接！", ws);
    };
    // 客户端接收服务端数据时触发
    socket.onmessage = (ws) => {
        console.log("接收到消息 >> ", ws.data);
    };
    // 连接关闭时触发
    socket.onclose = (ws) => {
        console.log("连接已断开！", ws);
    };
    // 通信发生错误时触发
    socket.onerror = (ws) => {
        console.log("发送错误！", ws);
    };
    // 2秒后向服务端发送消息
    setTimeout(() => {
        // 使用连接发送数据
        socket.send("发送一条消息试试");
    }, 2000);
    // 5秒后断开连接
    setTimeout(() => {
        // 关闭连接
        socket.close();
    }, 5000);
</script>
</body>
</html>
```

- `websocket2.html`内容如下

```html
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8">
    <title>菜鸟教程(runoob.com)</title>
    <script type="text/javascript">
        function WebSocketTest()
        {
            if ("WebSocket" in window)
            {
                alert("您的浏览器支持 WebSocket!");
                // 打开一个 web socket
                var ws = new WebSocket("ws://localhost:8082/websocket/1");
                ws.onopen = function()
                {
                    // Web Socket 已连接上，使用 send() 方法发送数据
                    ws.send("发送数据");
                    alert("数据发送中...");
                };
                ws.onmessage = function (evt)
                {
                    var received_msg = evt.data;
                    alert("数据已接收...");
                };
                ws.onclose = function()
                {
                    // 关闭 websocket
                    alert("连接已关闭...");
                };
            }
            else
            {
                // 浏览器不支持 WebSocket
                alert("您的浏览器不支持 WebSocket!");
            }
        }
    </script>
</head>
<body>
<div id="sse">
    <a href="javascript:WebSocketTest()">运行 WebSocket</a>
</div>
</body>
</html>

```

在浏览器请求http://localhost:8082/websocket2.html

> http://localhost:8082/websocket.html也可以进行测试，区别在于websocket.html是在打开页面的时候里面自动去连接websocket服务，websocket2.html是还需要点击一下才去连接。

![](https://user-gold-cdn.xitu.io/2019/10/27/16e0c73d260fea24?w=870&h=870&f=png&s=63269)

emmm，失败的，还没找到原因，下文整合springboot，测试是成功的。

## 整合springboot

使用springboot内置tomcat时，就不需要引入**javaee-api**了，spring-boot已经包含了。

springboot的高级组件会自动引用基础的组件，像**spring-boot-starter-websocket**就引入了**spring-boot-starter-web和spring-boot-starter**，所以不要重复引入

springboot已经做了深度的集成和优化，注意是否添加了不需要的依赖、配置或声明。由于很多讲解组件使用的文章是和spring集成的，会有一些配置。在使用springboot时，由于springboot已经有了自己的配置，再这些配置有可能导致各种各样的异常。

### pom.xml配置

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>javasea-web-websocket</artifactId>
        <groupId>org.springframework.boot</groupId>
        <version>2.1.5.RELEASE</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>javasea-web-websocket-springb</artifactId>

    <dependencies>
        <!-- https://mvnrepository.com/artifact/javax/javaee-api -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-websocket</artifactId>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
        </dependency>
    </dependencies>
    <build>
        <plugins> <!-- java编译插件 -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.7.0</version>
                <configuration>
                    <source>1.8</source>
                    <target>1.8</target>
                    <!-- 使用jdk进行编译 -->
                    <fork>true</fork>
                    <encoding>UTF-8</encoding>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```



springboot有两种方式实现websocket

- 通过注解`@ServerEndpoint`方式实现

webSocket核心是`@ServerEndpoint`这个注解。这个注解是**Javaee**标准里的注解，tomcat7以上已经对其进行了实现，如果是用传统方法使用tomcat发布的项目，只要在pom文件中引入**javaee**标准即可使用。

快速入门中的例子就是通过@ServerEndpoint来实现的WebSocket服务，在整合springboot的时候需要额外配置Config类，创建一个`ServerEndpointExporter();`

```java
@Configuration
public class WebSocketConfig {
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
```

- 使用实现`WebSocketConfigurer`接口的方式实现

  <font color = red>下文就是这种方式的实现</font>

### 编写WebSocketHandler

在Spring中，处理消息的具体业务逻辑需要实现WebSocketHandler接口。

```java
package com.javasea.web.websocket.springb.websocket;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

/**
 * @Description 在Spring中，处理消息的具体业务逻辑需要实现WebSocketHandler接口。
 * @Author longxiaonan@163.com
 * @Date 16:50 2019/10/27 0027
 **/
public class MyHandler extends TextWebSocketHandler {
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        System.out.println("获取到消息 >> " + message.getPayload());
        session.sendMessage(new TextMessage("消息已收到"));
        if (message.getPayload().equals("10")) {
            for (int i = 0; i < 10; i++) {
                //回写消息到client
                session.sendMessage(new TextMessage("消息 -> " + i));
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        session.sendMessage(new TextMessage("欢迎连接到ws服务"));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("断开连接！");
    }
}
```

### 编写配置类来实现WebSocket服务

```java
package com.javasea.web.websocket.springb.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(myHandler(), "/ws").setAllowedOrigins("*");
    }

    @Bean
    public WebSocketHandler myHandler() {
        return new MyHandler();
    }
}
```

### 编写启动类

```java
package com.javasea.web.websocket.springb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class APPlication {
    public static void main(String[] args) {
        SpringApplication.run(APPlication.class, args);
    }
}
```

### 测试

在线进行测试，url：ws://localhost:8080/ws

![](https://user-gold-cdn.xitu.io/2019/10/27/16e0c7afbeb104ad?w=1024&h=861&f=png&s=194575)

> 用上文的html页面也可以测试的，修改地址为`ws://localhost:8080/ws`然后在文件夹下直接用浏览器打开即可。

### 添加拦截器

```java
package com.javasea.web.websocket.springb.websocket;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Component
public class MyHandshakeInterceptor implements HandshakeInterceptor {
    /*** 握手之前，若返回false，则不建立链接 */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception { //将用户id放入socket处理器的会话(WebSocketSession)中
        attributes.put("uid", 1001);
        System.out.println("开始握手。。。。。。。");
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
        System.out.println("握手成功啦。。。。。。");
    }
}

```

将拦截器添加到websocket服务中：

> 就是在上文的config中添加`addInterceptors(this.myHandshakeInterceptor);`。

```java
package com.javasea.web.websocket.springb.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private MyHandshakeInterceptor myHandshakeInterceptor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(myHandler(), "/ws").setAllowedOrigins("*").addInterceptors(this.myHandshakeInterceptor);
    }

    @Bean
    public WebSocketHandler myHandler() {
        return new MyHandler();
    }
}
```

### 测试拦截器

在`MyHandler`类`afterConnectionEstablished`方法下输出获取到的`uid`。

```java
@Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("uid =>" + session.getAttributes().get("uid"));
        session.sendMessage(new TextMessage("欢迎连接到ws服务"));
    }
```

连接websocket服务 `ws://localhost:8080/ws`，console输出：

```
握手成功啦。。。。。。
uid =>1001
```

说明测试成功。

## 项目地址

github地址：https://github.com/longxiaonan/java-sea/tree/master/javasea-web/javasea-web-websocket/javasea-web-websocket-springboot

## 参考

<https://juejin.im/post/5c6cd80f6fb9a049dc02d3bc>

https://blog.csdn.net/hry2015/article/details/79829616
