package com.sudoku.framework.security.model;

import com.sudoku.project.model.vo.UserVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("登录生成显示类")
public class LoginSuccessVO implements Serializable {

  private static final long serialVersionUID = 1719529345713715975L;

  @ApiModelProperty("用户显示层对象")
  private UserVO user;

  @ApiModelProperty("登录令牌")
  private String token;
}
