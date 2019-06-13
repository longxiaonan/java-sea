
## 策略模式订单案例，通过订单注解的方式扫描实现减少if else判断方式
参考自：http://www.ciphermagic.cn/spring-boot-without-if-else.html
见包：
com.iee.patterns.example.strategy.order
### 测试：
[访问url：http://localhost:8080/strategy/order/1](http://localhost:8080/strategy/order/1)
返回：处理普通订单
[访问url：http://localhost:8080/strategy/order/2](http://localhost:8080/strategy/order/2)
返回：处理团购订单
[访问url：http://localhost:8080/strategy/order/3](http://localhost:8080/strategy/order/3)
返回：处理促销订单
