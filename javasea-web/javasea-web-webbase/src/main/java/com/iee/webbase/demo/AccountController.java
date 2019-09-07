package com.iee.webbase.demo;

import com.iee.webbase.common.BusinessException;
import com.iee.webbase.common.ResultBean;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

/**
 * 用于演示的账户相关服务
 *
 * @author dadiyang
 * @since 2019-06-02
 */
@Slf4j
//@Validated
@RestController
@RequestMapping("account")
@RequiredArgsConstructor
@Api(tags = "账户相关服务")
public class AccountController {
    private final AccountService accountService;

    @GetMapping("{id}")
    @ApiOperation(value = "通过id获取账户")
    public ResultBean<Account> getAccount(@ApiParam(name = "id", value = "账户id", required = true)
                                          @Positive(message = "主键必须大于0")
                                          @PathVariable("id") int id) {
        return ResultBean.success(accountService.getById(id));
    }

    @GetMapping(value = "get")
    @ApiOperation(value = "新增账户", notes = "返回新生成的主键id")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "account", value = "account帐号信息", dataType = "Account"),
    })
    public ResultBean<Integer> get(@Valid @RequestParam Account account) {
        return new ResultBean<>(accountService.insert(account));
    }

    //@Valid也可以用@Validated替换，不指定group，那么就不对bean中加了group的属性进行验证
    @PostMapping(value = "add", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "新增账户", notes = "返回新生成的主键id")
    public ResultBean<Integer> add(@Valid @RequestBody Account account) {
        return new ResultBean<>(accountService.insert(account));
    }

    //如果@Validated添加class的group，那么只对添加了group class属性进行校验
    //如果要对不加group class的属性也进行校验，那么需要添加Default.class到group
    @PostMapping(value = "add2", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "新增账户", notes = "返回新生成的主键id")
    public ResultBean<Integer> add2(@Validated(Insert.class) @RequestBody Account account) {
        return new ResultBean<>(accountService.insert(account));
    }

    @GetMapping("/testBusinessException")
    @ApiOperation(value = "用于测试抛出一个业务异常")
    public ResultBean<?> testBusinessException() {
        throw new BusinessException("对不起，发生了一个业务异常，请稍候重试");
    }

    @GetMapping("/testUnknownException")
    @ApiOperation(value = "用于测试抛出一个未知异常")
    public ResultBean<?> testUnknownException() throws Exception {
        throw new Exception("好好反省");
    }

    public void test4(){

    }
}
