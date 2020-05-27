https://juejin.im/post/5c495e85e51d45358e42a447#comment

## WebSocket与http有啥区别

### 相同点

  都建立在TCP之上，通过TCP协议来传输数据。

### 不同点

  HTTP协议为单向协议，即浏览器只能向服务器请求资源，服务器才能将数据传送给浏览器，而服务器不能主动向浏览器传递数据。分为长连接和短连接，短连接是每次http请求时都需要三次握手才能发送自己的请求，每个request对应一个response；长连接是短时间内保持连接，保持TCP不断开，指的是TCP连接。

  WebSocket一种双向通信协议，在建立连接后，WebSocket服务器和客户端都能主动的向对方发送或接收数据，就像Socket一样，不同的是WebSocket是一种建立在Web基础上的一种简单模拟Socket的协议；WebSocket需要通过握手连接，类似于TCP它也需要客户端和服务器端进行握手连接，连接成功后才能相互通信。WebSocket在建立握手连接时，数据是通过http协议传输的，“GET/chat HTTP/1.1”，这里面用到的只是http协议一些简单的字段。但是在建立连接之后，真正的数据传输阶段是不需要http协议参与的。

### 用处

  WebSocket解决客户端发起多个http请求到服务器资源浏览器必须要经过长时间的轮询问题。





## 为什么需要WebSocket?

大家都知道以前客户端想知道服务端的处理进度，要不停地使用 Ajax 进行轮询，让浏览器隔个几秒就向服务器发一次请求，这对服务器压力较大。另外一种轮询就是采用 long poll 的方式，这就跟打电话差不多，没收到消息就一直不挂电话，也就是说，客户端发起连接后，如果没消息，就一直不返回 response 给客户端，连接阶段一直是阻塞的。

而 WebSocket 解决了 HTTP 的这几个难题。当服务器完成协议升级后（ HTTP -> WebSocket ），服务端可以主动推送信息给客户端，解决了轮询造成的同步延迟问题。由于 WebSocket 只需要一次 HTTP 握手，服务端就能一直与客户端保持通信，直到关闭连接，这样就解决了服务器需要反复解析 HTTP 协议，减少了资源的开销。

作者：Joyu_chen

链接：https://juejin.im/post/5da824fff265da5bad406716

来源：掘金

著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。



![websockets](https://user-gold-cdn.xitu.io/2019/10/17/16dd8d1183bc66ac?imageView2/0/w/1280/h/960/format/webp/ignore-error/1)