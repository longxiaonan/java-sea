package com.zhirui.lmwy.wms.demo.web.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.zhirui.lmwy.common.persistence.constraints.Phone;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
//@Builder
@ApiModel
public class Student implements Serializable {

    private Integer id;

    private String name;

    /** 手机号*/
    @Phone
    private String mobile;

    /** 邮箱*/
    @Email
    private String email;

    @JsonFormat(pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy/MM/dd")
    private Date birth;

    private LocalDateTime createTime;

    private Integer age;

    //学分
    private Double credit;

}
