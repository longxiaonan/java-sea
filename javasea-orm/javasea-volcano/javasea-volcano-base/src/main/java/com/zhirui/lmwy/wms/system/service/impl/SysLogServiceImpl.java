package com.zhirui.lmwy.wms.system.service.impl;

import com.zhirui.lmwy.wms.system.entity.SysLog;
import com.zhirui.lmwy.wms.system.mapper.SysLogMapper;
import com.zhirui.lmwy.wms.system.service.SysLogService;
import com.zhirui.lmwy.wms.system.web.param.SysLogQueryParam;
import com.zhirui.lmwy.wms.system.web.vo.SysLogQueryVo;
import com.zhirui.lmwy.mgr.web.service.impl.BaseServiceImpl;
import com.zhirui.lmwy.mgr.web.vo.Paging;
import com.zhirui.lmwy.mgr.persistence.enums.OrderEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.io.Serializable;


/**
 * <p>
 * 系统日志 服务实现类
 * </p>
 *
 * @author longxiaonan
 * @since 2019-11-20
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class SysLogServiceImpl extends BaseServiceImpl<SysLogMapper, SysLog> implements SysLogService {

    @Autowired
    private SysLogMapper sysLogMapper;

    @Override
    public SysLogQueryVo getSysLogById(Serializable id) throws Exception{
        return sysLogMapper.getSysLogById(id);
    }

    @Override
    public Paging<SysLogQueryVo> getSysLogPageList(SysLogQueryParam sysLogQueryParam) throws Exception{
        Page page = setPageParam(sysLogQueryParam,"create_time", OrderEnum.DESC);
        IPage<SysLogQueryVo> iPage = sysLogMapper.getSysLogPageList(page,sysLogQueryParam);
        return new Paging(iPage);
    }

}
