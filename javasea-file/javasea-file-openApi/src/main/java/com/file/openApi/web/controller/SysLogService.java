package com.file.openApi.web.controller;

import com.file.openApi.common.param.page.Paging;
import com.file.openApi.entity.SysLog;
import com.file.openApi.web.param.SysLogQueryParam;
import com.file.openApi.web.vo.SysLogQueryVo;
import org.springframework.stereotype.Service;

/**
 * @ClassName SysLogService
 * @Description 测试的service，所以无逻辑
 * @Author longxiaonan@163.com
 * @Date 2019/10/15 0015 18:57
 */
@Service
public class SysLogService {
    public boolean save(SysLog sysLog) {
        return false;
    }

    public boolean updateById(SysLog sysLog) {
        return false;
    }

    public boolean removeById(String id) {
        return false;
    }

    public SysLogQueryVo getSysLogById(String id) {
        return null;
    }

    public Paging<SysLogQueryVo> getSysLogPageList(SysLogQueryParam sysLogQueryParam) {
        return null;
    }
}
