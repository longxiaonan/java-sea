package com.iee.orm.mybatis.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * <p>
 * 文件
 * </p>
 *
 * @author longxiaonan
 * @since 2019-11-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_file")
public class File implements Serializable {

    private static final long serialVersionUID = 1L;

/**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
private Integer id;

/**
     * 文件名
     */
private String fileName;

/**
     * 创建者
     */
private String createBy;

/**
     * 租户id
     */
private String tenantId;

}
