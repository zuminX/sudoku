package com.sudoku.model.body;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotEmpty;
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

  @ApiModelProperty(value = "用户名", required = true)
  @NotEmpty(message = "用户名不能为空")
  @Length(min = 4, max = 16, message = "用户名的长度为4-16位")
  private String username;

  @ApiModelProperty(value = "密码", required = true)
  @NotEmpty(message = "密码不能为空")
  @Length(min = 6, max = 32, message = "密码的长度为6-32位")
  private String password;

  @ApiModelProperty(value = "重复密码", required = true)
  @NotEmpty(message = "重复密码不能为空")
  @Length(min = 6, max = 32, message = "重复密码的长度为6-32位")
  private String repeatPassword;

  @ApiModelProperty(value = "用户昵称", required = true)
  @NotEmpty(message = "昵称不能为空")
  @Length(min = 4, max = 32, message = "昵称的长度为4-32位")
  private String nickname;

  @ApiModelProperty(value = "验证码", required = true)
  @NotEmpty(message = "验证码不能为空")
  @Length(min = 5, max = 5, message = "验证码必须为5位")
  private String code;

  @ApiModelProperty(value = "唯一标识", required = true)
  private String uuid;
}
