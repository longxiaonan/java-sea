package com.file.openApi.web.controller;

import com.file.openApi.common.param.IdParam;
import com.file.openApi.common.param.page.Paging;
import com.file.openApi.common.result.ResultModel;
import com.file.openApi.entity.SysLog;
import com.file.openApi.web.param.SysLogQueryParam;
import com.file.openApi.web.vo.SysLogQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 系统日志 前端控制器
 * </p>
 *
 * @author longxiaonan
 * @since 2019-09-12
 */
@RestController
@RequestMapping("/sysLog")
@Slf4j
@Api(tags = {"系统日志 API1111"})
public class SysLogController {

    @Autowired
    private SysLogService sysLogService;

    /**
    * 添加系统日志
    */
    @PostMapping("/sysLog")
    @ApiOperation(value = "添加SysLog对象",notes = "添加系统日志",response = ResultModel.class)
//    @ApiImplicitParam(name = "sysLog", value = "用户详细实体user", required = true, dataType="SysLog")
//    @ApiImplicitParams({@ApiImplicitParam(paramType = "body", name = "sysLog", value = "sysLog")})
    public ResultModel<Boolean> addSysUser(@Valid @RequestBody SysLog sysLog) throws Exception{
        boolean flag = sysLogService.save(sysLog);
        return ResultModel.result(flag);
    }

    /**
    * 修改系统日志
    */
    @PutMapping("/sysLog")
    @ApiOperation(value = "修改SysLog对象",notes = "修改系统日志",response = ResultModel.class)
    public ResultModel<Boolean> updateSysUser(@Valid @RequestBody SysLog sysLog) throws Exception{
        boolean flag = sysLogService.updateById(sysLog);
        return ResultModel.result(flag);
    }

    /**
    * 删除系统日志
    */
    @DeleteMapping("/sysLog")
    @ApiOperation(value = "删除SysLog对象",notes = "删除系统日志",response = ResultModel.class)
    public ResultModel<Boolean> deleteSysUser(@Valid @RequestBody IdParam idParam) throws Exception{
        boolean flag = sysLogService.removeById(idParam.getId());
        return ResultModel.result(flag);
    }

    /**
    * 获取系统日志
    */
    @GetMapping("/sysLog")
    @ApiOperation(value = "获取SysLog对象详情",notes = "查看系统日志",response = SysLogQueryVo.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "idParam", value = "页码", required = true, dataType = "IdParam"),
            @ApiImplicitParam(name = "pageNum", value = "每页个数", required = true, dataType = "int")
    })
    public ResultModel<SysLogQueryVo> getSysUser(@Valid IdParam idParam, Integer pageNum, int pageSize) throws Exception{
        SysLogQueryVo sysLogQueryVo = sysLogService.getSysLogById(idParam.getId());
        return ResultModel.ok(sysLogQueryVo);
    }

    /**
     * 系统日志分页列表
     */
    @GetMapping("/page")
    @ApiOperation(value = "获取SysLog分页列表",notes = "系统日志分页列表")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", name = "syslog", value = "syslog", dataType = "SysLogQueryParam")})
    public ResultModel<Paging<SysLogQueryVo>> getSysLogPageList(@Valid SysLogQueryParam sysLogQueryParam) throws Exception{
        Paging<SysLogQueryVo> paging = sysLogService.getSysLogPageList(sysLogQueryParam);
        return ResultModel.ok(paging);
    }

}

