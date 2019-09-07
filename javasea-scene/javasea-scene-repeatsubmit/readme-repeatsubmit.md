思路：
1. 自定义注解 @NoRepeatSubmit 标记所有Controller中的提交请求

2. 通过AOP 对所有标记了 @NoRepeatSubmit 的方法拦截

3. 在业务方法执行前，获取当前用户的 token（或者JSessionId）+ 当前请求地址，作为一个唯一 KEY，去获取 Redis 分布式锁（如果此时并发获取，只有一个线程会成功获取锁）

4. 业务方法执行后，释放锁

