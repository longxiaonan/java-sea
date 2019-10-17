package com.file.openApi.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * <p>
 * 系统日志
 * </p>
 *
 * @author longxiaonan
 * @since 2019-09-12
 */
@Data
@EqualsAndHashCode()
@ApiModel(value = "SysLog对象", description = "系统日志")
public class SysLog {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long logId;

    @ApiModelProperty(value = "类型")
//    @ApiIgnore //使用这个注解忽略这个接口
    private Boolean type;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "创建人ID")
    private Long createId;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

}
