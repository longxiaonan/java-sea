package com.javasea.workflow.activiti.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName Holiday
 * @Description pojo存储到流程变量的时候需要实现序列化接口
 * @Author longxiaonan@163.com
 * @Date 2019/9/9 0009 17:28
 */
@Data
public class Holiday implements Serializable {
    private Integer id;
    private String holidayName; //申请人名字
    private Date beginDate; //开始日期
    private Date endDate; //结束日期
    private Float num; //请假天数
    private String reason; //请假理由
    private String type;
}
