
## 策略模式订单案例1，通过订单注解的方式扫描实现减少if else判断方式
参考自：http://www.ciphermagic.cn/spring-boot-without-if-else.html
见包：
com.iee.patterns.example.strategy.orderannotation
### 测试：
[访问url：http://localhost:8080/strategy/order/1](http://localhost:8080/strategy/order/1)
返回：处理普通订单

[访问url：http://localhost:8080/strategy/order/2](http://localhost:8080/strategy/order/2)
返回：处理团购订单

[访问url：http://localhost:8080/strategy/order/3](http://localhost:8080/strategy/order/3)
返回：处理促销订单

## 策略模式订单案例2，通过继承后map缓存策略子类的方式减少if else判断方式
参考自：https://juejin.im/post/5d12228de51d45775c73dd1b?utm_source=gold_browser_extension

见包：
com.iee.patterns.example.strategy.orderregister
### 测试：
启动测试类：com.iee.patterns.example.strategy.orderregister.InspectionTest.test

##
##
