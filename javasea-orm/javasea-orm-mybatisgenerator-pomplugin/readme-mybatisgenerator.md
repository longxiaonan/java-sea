
### 运行maven指令为：
mvn mybatis-generator:generate -e

### 如何使用：
单条件模糊搜索 + 排序
```java
@Autowired
private UserInfoMapper userInfoMapper;

public List<UserInfo> searchUserByUserName( String userName ) {

    UserInfoExample userInfoExample = new UserInfoExample();
    userInfoExample.createCriteria().andUserNameLike( '%'+ userName +'%' ); // 设置模糊搜索的条件

    String orderByClause = "user_name DESC";
    userInfoExample.setOrderByClause( orderByClause );  // 设置通过某个字段排序的条件

    return userInfoMapper.selectByExample( userInfoExample );
}
```
多条件：
```java
public List<UserInfo> multiConditionsSearch( UserInfo userInfo ) {

    UserInfoExample userInfoExample = new UserInfoExample();
    UserInfoExample.Criteria criteria = userInfoExample.createCriteria();

    if( !"".equals(userInfo.getPhone()) )
        criteria.andPhoneEqualTo( userInfo.getPhone() );
    if( !"".equals(userInfo.getUserName()) )
        criteria.andUserNameEqualTo( userInfo.getUserName() );

    return userInfoMapper.selectByExample( userInfoExample );
}
```
