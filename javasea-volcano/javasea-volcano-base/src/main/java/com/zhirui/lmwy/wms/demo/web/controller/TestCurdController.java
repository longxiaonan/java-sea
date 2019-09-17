package com.zhirui.lmwy.wms.demo.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Maps;
import com.zhirui.lmwy.common.persistence.model.result.ResultModel;
import com.zhirui.lmwy.wms.demo.web.entity.User;
import com.zhirui.lmwy.wms.demo.web.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @ClassName TestController
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2019/9/3 0003 0:34
 */
@RequestMapping("sys")
@RestController
@Api(tags = { "一个用来测试curd和swagger注解的控制器" })
public class TestCurdController {

    @Autowired
    UserService userService;

    @GetMapping("/users/{id}")
    @ApiOperation(value = "通过id获取用户")
    public ResultModel getAccount(@ApiParam(name = "id", value = "用户id", required = true)
                                          @Positive(message = "主键必须大于0")
                                          @PathVariable int id) {
        return ResultModel.ok(userService.getById(id));
    }

    @GetMapping("/users")
    @ApiOperation(value = "按照条件分页查询")
    public ResultModel getAccount(@RequestParam @Valid User user) { //如果是需要安装User实体的校验注解进行校验，那么需要在controller添加@Validated
        IPage<User> pageList = userService.lambdaQuery().like(User::getUsername, "long").page(new Page<>(1, 2));
        return ResultModel.ok(pageList);
    }

    @PostMapping("/user")
    @ApiOperation(value = "插入一个用户")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "body", name = "user", value = "user", dataType = "User")})
    public ResultModel save(@Valid @RequestBody User user){
        //如果大于1条会报错，所有，第三个参数为false
        User user1 = User.builder().username("刘备").age(30).build();
        user1.setUid(Long.MAX_VALUE - 1);
        boolean save = this.userService.save(user1);
//        return save ? ResultModel.insertSuccess() : ResultModel.insertFail();
        return ResultModel.result(save);
    }

    @PostMapping("/users")
    @ApiOperation(value = "批量插入用户")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "body", name = "userList", value = "userList", dataType = "List<User>")})
    public void saveBatch(@Valid @RequestBody List<User> userList){
        //如果大于1条会报错，所有，第三个参数为false
        User user1 = User.builder().username("关羽").age(28).build();
        User user2 = User.builder().uid(27L).username("张飞").age(27).build();//如果设置id用saveOrUpdateBatch，那么会先查询后更新或者插入
        List<User> users = Arrays.asList(user1, user2);
        this.userService.saveOrUpdateBatch(users);

    }

    @PutMapping("/users")
    @ApiOperation(value = "批量更新")
    public List<User> updateBatch(){
        List<User> aLong = userService.lambdaQuery().like(User::getUsername, "long").gt(User::getAge, 22).list();
        return aLong;
    }

    @GetMapping("/lambdaSelect")
    public List<User> lambdaSelect(){
        List<User> aLong = userService.lambdaQuery().like(User::getUsername, "long").lt(User::getAge, 22).list();
        return aLong;
    }

    @GetMapping("lambdaUpdate")
    public boolean lambdaUpdate(){
        Map<SFunction<User, ?>, Object> map = Maps.newHashMap();
        map.put(User::getUsername, "long");
        boolean update = userService.lambdaUpdate().allEq(map).set(User::getAge, 18).update();
//        boolean aLong = userService.lambdaUpdate().gt(User::getUsername, "long").like(User::getAge, 22).set(User::getAge, 26).update();
        return update;
    }

    @GetMapping("lambdaRemove")
    public boolean lambdaRemove(){
        boolean aLong = userService.lambdaUpdate().gt(User::getUsername, "long").like(User::getAge, 22).set(User::getAge, 26).remove();
        return aLong;
    }

}
