# spring scurity 和 jwt
参考自：https://mp.weixin.qq.com/s?__biz=MzU4ODI1MjA3NQ==&mid=2247484035&idx=1&sn=e92e92c550279e6c0484c39daa2a4361&chksm=fdded447caa95d514dd3dc299b8168945422a466829981a0841ab826d7de26f4474d810ecaf0&scene=21#wechat_redirect

## 测试
### 注册
POST: http://localhost:8800/authentication/register
参数：
```
{
 "username":"lognxn",
 "password":"123"
}
```
head设置：
Content-Type: application/json

### 登陆
POST: http://localhost:8800/authentication/login
参数：
username: lognxn
password: 123

head设置：
Content-Type: application/x-www-form-urlencoded

### 访问权限测试接口
GET: http://localhost:8800/normal/test
返回：
```
{
"timestamp": "2019-06-25T15:25:24.686+0000",
"status": 403,
"error": "Forbidden",
"message": "Access Denied",
"path": "/normal/test"
}
```
不带 Token直接访问需要普通角色（ ROLE_NORMAL）的接口 /normal/test会直接提示访问不通：
