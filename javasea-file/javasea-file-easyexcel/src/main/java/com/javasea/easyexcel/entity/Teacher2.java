package com.javasea.easyexcel.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.javasea.easyexcel.relase.merge.GenericMerge;
import com.javasea.easyexcel.relase.merge.GenericMergeBO;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @ClassName Teacher
 * @Description
 *
 * ExcelProperty 指定当前字段对应excel中的那一列
 * ExcelIgnore 默认所有字段都会和excel去匹配，加了这个注解会忽略该字段
 * DateTimeFormat 日期转换，用String去接收excel日期格式的数据会调用这个注解。里面的value参照java.text.SimpleDateFormat
 * NumberFormat 数字转换，用String去接收excel数字格式的数据会调用这个注解。里面的value参照java.text.DecimalFormat
 *
 * @Author longxiaonan@163.com
 * @Date 2020/3/23 0023 17:22
 */
@Data
@NoArgsConstructor
public class Teacher2 implements GenericMerge {


    /** 老师id */
    @ExcelIgnore
    private Integer teacherId;
    /** 老师姓名 */
    @ExcelProperty("老师姓名")
    @NotNull(message = "姓名不能为空")
    private String teacherName;
    /** 头像地址 */
    @ExcelProperty("头像地址")
    private String teacherImage;
    /** 删除状态 */
    @ExcelProperty("删除状态")
    private Integer teacherStatus;


    @ExcelProperty("学号")
    private String studentNo;
    @ExcelProperty("学生姓名")
    private String studentName;
    @ExcelProperty("教室")
    private String clazzRoom;

    @ExcelProperty("宠物名称")
    private String petName;
    @ExcelProperty("宠物分类")
    private String petCategory;

    @ExcelIgnore
    private Boolean isRowSpan;
    @ExcelIgnore
    private Integer rowIndexNumber;
    @ExcelIgnore
    private Boolean isStudentRowSpan;
    @ExcelIgnore
    private Integer studentRowIndexNumber;

    @Override
    public GenericMergeBO merge(int columnIndex) {
        GenericMergeBO bo = null;
        if (columnIndex == 0 || columnIndex == 1 || columnIndex == 2 || columnIndex == 3 || columnIndex == 4 || columnIndex == 5) {
            if (columnIndex == 0 || columnIndex == 1 || columnIndex == 2 || columnIndex == 3) {
                bo = new GenericMergeBO();
                bo.setStartMergeCell(this.isRowSpan);
                bo.setRowspan(this.rowIndexNumber);
                bo.setColspan(1);
                return bo;
            }
            if (columnIndex == 4 || columnIndex == 5) {
                bo = new GenericMergeBO();
                bo.setStartMergeCell(this.isStudentRowSpan);
                bo.setRowspan(this.studentRowIndexNumber);
                bo.setColspan(1);
                return bo;
            }
        }
        return bo;
    }

}
