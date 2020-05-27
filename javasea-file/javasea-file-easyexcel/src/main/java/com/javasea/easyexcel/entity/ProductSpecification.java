package com.javasea.easyexcel.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 产品类型下的具体产品规格
 * </p>
 *
 * @author longxiaonan
 * @since 2019-12-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
//@Accessors(chain = true)   // 官网规定不能为true，因为通过反射来的，否则会导致不能设置值
public class ProductSpecification {

    /**
     * 规格编码
     */
    @ExcelProperty("名称")
    private String code;

    /**
     * 规格组
     */
    @ExcelProperty("规格组")
    private String specGroup;

    /**
     * 长度(mm)
     */
    @ExcelProperty("长度")
    private Integer length;

    /**
     * 宽度(mm)
     */
    @ExcelProperty("宽度")
    private Integer width;
}
