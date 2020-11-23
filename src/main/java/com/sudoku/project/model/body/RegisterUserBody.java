package com.sudoku.project.model.body;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel("注册用户信息体类")
public class RegisterUserBody implements Serializable {


  private static final long serialVersionUID = 7615402214779318417L;

  @ApiModelProperty("用户名")
  @Length(min = 4, max = 16, message = "用户名的长度为4-16位")
  private String username;

  @ApiModelProperty("密码")
  @Length(min = 6, max = 32, message = "密码的长度为6-32位")
  private String password;

  @ApiModelProperty("重复密码")
  @Length(min = 6, max = 32, message = "重复密码的长度为6-32位")
  private String repeatPassword;

  @ApiModelProperty("用户昵称")
  @Length(min = 4, max = 16, message = "昵称的长度为4-16位")
  private String nickname;

  @ApiModelProperty("验证码")
  @Length(min = 4, max = 4, message = "验证码必须为4位")
  private String code;

  @ApiModelProperty("唯一标识")
  private String uuid;
}
