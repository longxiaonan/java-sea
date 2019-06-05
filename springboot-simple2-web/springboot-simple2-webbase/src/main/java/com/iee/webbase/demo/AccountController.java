package com.iee.webbase.demo;

import com.iee.webbase.common.BusinessException;
import com.iee.webbase.common.ResultBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Validated
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

    @PostMapping(value = "add", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "新增账户", notes = "返回新生成的主键id")
    public ResultBean<Integer> add(@Valid @RequestBody Account account) {
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
}
