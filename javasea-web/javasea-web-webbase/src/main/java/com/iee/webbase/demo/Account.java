package com.iee.webbase.demo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Insert;

import javax.validation.GroupSequence;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * @author dadiyang
 * @since 2019-06-02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
public class Account implements Serializable {
    private static final int MAX_BALANCE = 1_000_000;
    /**
     * 主键
     */
    @ApiModelProperty(value = "账户id", required = true)
    private Integer id;

    /**
     * 姓名
     */
    @NotBlank(message = "姓名不能为空")
    @ApiModelProperty(value = "姓名", notes = "不能为空", required = true)
    private String name;

    @NotBlank(groups = Insert.class, message = "密码不能获取")
    @ApiModelProperty(value = "密码", required = true)
    private String password;

    /**
     * 余额
     */
    @Max(value = MAX_BALANCE, message = "余额不能超过 " + MAX_BALANCE)
    @PositiveOrZero(message = "余额必须大于等于0")
    private Double balance;

    public Account(String name, Double balance) {
        this.name = name;
        this.balance = balance;
    }

    public Account(int id, String name, double balance) {
        this.id = id;
        this.name = name;
        this.balance = balance;
    }
}
