package com.file.openApi.web.param;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统日志 查询参数对象
 * </p>
 *
 * @author longxiaonan
 * @date 2019-09-12
 */
@Data
@EqualsAndHashCode()
@ApiModel(value="SysLogQueryParam对象", description="系统日志查询参数")
public class SysLogQueryParam {
    private static final long serialVersionUID = 1L;
}
