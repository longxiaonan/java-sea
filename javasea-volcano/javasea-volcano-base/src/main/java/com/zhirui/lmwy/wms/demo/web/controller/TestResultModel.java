package com.zhirui.lmwy.wms.demo.web.controller;

import com.zhirui.lmwy.common.persistence.model.result.ResultModel;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName TestResultModel
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2019/9/17 0017 13:23
 */
@RestController
public class TestResultModel {

    //插入后返回方式， msg：插入成功；插入失败
    public ResultModel resultInsert(){
        //插入后返回值
        boolean flag = false;
        return ResultModel.resultInsert(flag);
    }

    //更新后返回方式， msg：更新成功；更新失败
    public ResultModel resultUpdate(){
        //插入后返回值
        boolean flag = false;
        return ResultModel.resultUpdate(flag);
    }

    //删除后返回方式， msg：删除成功；删除失败
    public ResultModel resultDelete(){
        //插入后返回值
        boolean flag = false;
        return ResultModel.resultDelete(flag);
    }

    //删除后返回方式， msg：操作成功；操作失败
    public ResultModel result(){
        //插入后返回值
        boolean flag = false;
        return ResultModel.result(flag);
    }

    //认证失败返回方式， msg：认证信息异常
    public ResultModel errorTokenMsg(){
        //插入后返回值
        boolean flag = false;
        //如果msg参数为null，那么是默认的msg：认证信息异常
        return ResultModel.errorTokenMsg(null);
    }

}
