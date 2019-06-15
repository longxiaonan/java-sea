### mybatis-plus介绍
一个基于mybatis的自动代码生成器工具，是gitee上的明星项目。
可以自动生成mapper层接口，sql和service层接口等等。
gitee地址为：https://gitee.com/baomidou/mybatis-plus
官网为：https://mp.baomidou.com/

### 测试
>根据名称查询和分页查询有问题，待解决！！！
#### 插入：
`POST: http://localhost:8800/user/insert`
参数：
```java
{
  "userId":"111",
  "username":"lognxn",
  "password":"123"
}
```
#### 更新
`POST: http://localhost:8800/user/update`
参数：
```java
{
  "userId":"111",
  "username":"lognxn11",
  "password":"123111"
}

#### 删除
`POST: http://localhost:8800/user/delete`
参数：
```java
{
  "userId":"111",
  "username":"lognxn11",
  "password":"123111"
}



