package com.javasea.file.easyexcel.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName Teacher
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2020/3/5 0005 23:10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Teacher {
    /**
     *      * 老师的主键     
     */
    private Integer teacherId;
    /**
     *      * 名字     
     */
    @Excel(name = "老师名字")
    private String teacherName;
    /**
     *      * 头像图片地址     
     */
    @Excel(name = "头像地址")
    private String teacherImage;
    /**
     *      * 老师的状态 0代表正常 1代表删除     
     */

    private Integer teacherStatus;
}

