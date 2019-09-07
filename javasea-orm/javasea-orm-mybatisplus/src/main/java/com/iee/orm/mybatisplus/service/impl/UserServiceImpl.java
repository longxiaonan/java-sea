package com.iee.orm.mybatisplus.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iee.orm.mybatisplus.common.PageInfo;
import com.iee.orm.mybatisplus.entity.User;
import com.iee.orm.mybatisplus.mapper.UserMapper;
import com.iee.orm.mybatisplus.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    UserMapper userMapper;

    // 增
    @Override
    public int insertUser(User user) {
        return baseMapper.insert( user );
    }

    // 改, 通过id更新，其他不为空字段会在sql的set中
    @Override
    public int updateUser(User user) {
        return baseMapper.updateById( user );
    }

    // 改
    @Override
    public int updateUserByWrapper(User user) {
        //update 的 sql条件
//        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
//        wrapper.eq("name","long").eq("age",20);
//        wrapper.eq("name","long").eq("age",20).set("age", 30);//列少的时候可以直接set

        //update 的 lambda条件
        LambdaUpdateWrapper<User> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(User::getUsername, "long").eq(User::getAge, 19);

        user.setPassword("111");
        return baseMapper.update( user , wrapper);
    }

    // 删
    @Override
    public int deleteById(User user) {
        return baseMapper.deleteById( 1L);
    }

    // 删
    @Override
    public int deleteByMap(User user) {
        Map<String,Object> map = new HashMap<>();
        map.put("username", "longxiaonan");
        map.put("age", "16");
        return baseMapper.deleteByMap(map);
    }

    //查所有
    @Override
    public List<User> selectAll() {
        List<User> users = userMapper.selectList(null);
        return users;
    }

    //精确条件查询
    @Override
    public List<User> selectByMap() {
        Map<String,Object> map = new HashMap();
        //map的key是表中的列，而不是实体属性名
        map.put("username", "longxiaonan");
        List<User> users = userMapper.selectByMap(map);
        return users;
    }

    @Override
    public List<User> selectByWrapperAllEq() {
        Map<String,Object> map = new HashMap();
        //map的key是表中的列，而不是实体属性名
        map.put("username", "longxiaonan");
        map.put("password", null); //password IS NULL
        QueryWrapper queryWrapper = Wrappers.query();
        queryWrapper.allEq(map);
//        queryWrapper.allEq(map, false); //如果设置为false，那么就不会拼接到sql中
        List<User> users = userMapper.selectList(queryWrapper);
        return users;
    }

    //使用实体作为查询条件
    @Override
    public List<User> selectByWrapperEntity() {
        User user = new User();
        //相当于是username = longxiao, age = 20
//        如果要username like 那么在实体类加上@TableField(condition = SqlCondition.LIKE), condition等号右边的条件可以自己设置
        user.setUsername("longxiao");
        user.setAge(20);
        QueryWrapper<User> userWrapper = new QueryWrapper(user);
        return userMapper.selectList(userWrapper);
    }

    //返回值是List<Map>
    // 场景1：当表字段较多的时候，用selectList返回的实体类型就算指定了select，其他字段变成了null，不优雅。
    // 场景2：统计查询的时候
    @Override
    public List<Map<String, Object>> selectByWrapperMaps() {
        QueryWrapper<User> query = new QueryWrapper();
        query.select("username","password").in("age", Arrays.asList(18,19,20));
        return userMapper.selectMaps(query);
    }

    @Override
    public List<Map<String, Object>> selectByWrapperLambda() {
        LambdaQueryWrapper<User> lambdaQueryWrapper = Wrappers.lambdaQuery();
//        lambdaQueryWrapper.like(User::getUsername, "long");
        lambdaQueryWrapper.likeRight(User::getUsername,"long").and(wq -> wq.le(User::getAge, 20).or().isNotNull(User::getCreateTime));
        return userMapper.selectMaps(lambdaQueryWrapper);
    }

    //当不满足条件的时候实行自定义
    @Override
    public List<User> selectMy() {
        LambdaQueryWrapper<User> lambdaQueryWrapper = Wrappers.lambdaQuery();
//        lambdaQueryWrapper.like(User::getUsername, "long");
        lambdaQueryWrapper.likeRight(User::getUsername,"long").and(wq -> wq.le(User::getAge, 20).or().isNotNull(User::getCreateTime));
        List<User> users = userMapper.selectAll(lambdaQueryWrapper);
        return users;
    }

    //条件查询
    @Override
    public List<User> selectByWrapper() {
        //两种创建wrapper的方式
//        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        QueryWrapper<User> query = Wrappers.query();
        LambdaQueryWrapper lambdaQueryWrapper = Wrappers.lambdaQuery();
        //username列like %long%  and uid < 4
//        query.like("username", "long").lt("uid", 4);

        //如果username或者uid可能为空，那么通过第一个参数condition来判断，只有在condition为true时拼接到sql
        String username = "";
        query.like(StringUtils.isNotEmpty(username), "username", username).lt("uid", 4);

        //username列like long%  and uid < 4 或者 id = 5
//        query.likeLeft("username", "long").lt("uid", 4).or().eq("uid", 1).orderByDesc("uid");

        //创建时间是2019-09-01 且 直属上级是“long”, 实现子查询
//        query.apply("date_format(create_time,'%Y-%m-%d') = {0}", "2019-09-02").inSql("manage_id", "select uid from user where username like 'long%'");

        //username like long% and( age <= 20 or create_time is not null)
//        query.likeRight("username","long").and(wq -> wq.le("age", 20).or().isNotNull("create_time"));

        //username like long% or( age <= 20 and create_time is not null)
//        query.likeRight("username","long").or(wq -> wq.le("age", 20).isNotNull("create_time"));

        //( age <= 20 or create_time is not null) and username like long%
//        query.nested(wq -> wq.le("age", 20).isNotNull("create_time")).likeRight("username", "long");

        //age in (18,19,20)
//        query.in("age", Arrays.asList(18,19,20));

        //返回其中一条，last直接拼接的sql
//        query.in("age", Arrays.asList(18,19,20)).last("limit 1");

        //返回指定列
//        query.in("age", Arrays.asList(18,19,20)).select("username","age");
        //排除返回的指定列
        query.in("age", Arrays.asList(18,19,20)).select(User.class, info -> !info.getColumn().equals("create_time")&& !info.getColumn().equals("manage_id"));
        List<User> users = userMapper.selectList(query);
        return users;
    }


    // 查: 普通查
    @Override
    public User findUserByName( String userName ) {
        return userMapper.findUserByName( userName );
    }

    // 查：分页查
    @Override
    public IPage getUserPage(PageInfo pageInfo, User user) {
//        Page page = new Page();
//        page.setCurrent(pageInfo.getPageIndex());
//        page.setSize(pageInfo.getPageSize());
        Page<User> page = new Page(1,2);//如果不需要总记录数，那么第三个参数传false
        IPage iPage = userMapper.selectPage(page, null);
        long total = iPage.getTotal();//总记录数
        long pages = iPage.getPages();//总页数
        List records = iPage.getRecords();//所有记录
        return iPage;
    }


}
