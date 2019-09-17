
package com.zhirui.lmwy.wms.security.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 *  登录参数
 * </p>
 * @auth longxiaonan@aliyun.com
 * @date 2019-05-15
 **/
@Data
@ApiModel("登录参数")
public class LoginParam {

    @NotBlank(message = "请输入账号")
    @ApiModelProperty("账号")
    private String userName;

    @NotBlank(message = "请输入密码")
    @ApiModelProperty("密码")
    private String pwd;

    @ApiModelProperty("验证码")
    private String code;

}
