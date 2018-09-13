该项目是基于spring data redis, 在RedisConfig.java中自定义了序列化的方式和各种bean的产生方式.
封装了redis的bean创建和操作方式.提供操作的工具类.
配置文件见:
com.redis.config.RedisConfig
工具类见:
com.redis.utils.RedisUtil
测试用例见:
com.redis.controller.TestRedisController

其实springboot支持开箱即用功能, 无需定义RedisConfig.java也可以使用.
如下诉求导致促使我对他进行自定义bean:
1. 我在一个vdp-cloud项目中需要一个springboot整合redis的方案,且可以通过开关方式开启和关闭redis,所以需要关闭redis自动配置.
   关闭redis自动配置后,redis的相关bean就不会产生, 就需要手动配置redis的JedisConnectionFactory等各种bean.
2. 默认情况下spring.redis.host只能配置单机模式的hostname, 通过手动配置redis的JedisConnectionFactory后,host可以配置一个或者多个节点. 
   在创建JedisConnectionFactory时会自动根据节点数量判断创建单机模式的还是集群模式的JedisConnectionFactory.
3. spring data redis默认的序列化方式有string和jdk的序列化, 对象会进行jdk序列化并不友好, 所以对象修改成jackson的序列化方式.

RedisConfig.java参考:
源码

redis工具类参考：
https://www.imooc.com/article/23609

pringcache参考：
https://www.imooc.com/article/68953

序列化和工具类参考：
http://www.cnblogs.com/zeng1994/p/03303c805731afc9aa9c60dbbd32a323.html
