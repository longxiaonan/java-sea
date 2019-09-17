package com.zhirui.lmwy.wms.system.service;

import com.zhirui.lmwy.wms.system.entity.SysLog;
import com.zhirui.lmwy.common.web.service.BaseService;
import com.zhirui.lmwy.wms.system.web.param.SysLogQueryParam;
import com.zhirui.lmwy.wms.system.web.vo.SysLogQueryVo;
import com.zhirui.lmwy.common.web.vo.Paging;

import java.io.Serializable;

/**
 * <p>
 * 系统日志 服务类
 * </p>
 *
 * @author longxiaonan
 * @since 2019-09-12
 */
public interface SysLogService extends BaseService<SysLog> {
    /**
     * 根据ID获取查询对象
     * @param id
     * @return
     */
    SysLogQueryVo getSysLogById(Serializable id) throws Exception;

    /**
     * 获取分页对象
     * @param sysLogQueryParam
     * @return
     */
    Paging<SysLogQueryVo> getSysLogPageList(SysLogQueryParam sysLogQueryParam) throws Exception;

}
