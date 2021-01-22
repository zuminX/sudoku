package com.sudoku.project.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("用户显示类")
public class UserVO implements Serializable {

  private static final long serialVersionUID = -3781968958583675403L;

  @ApiModelProperty("用户ID")
  private Integer id;

  @ApiModelProperty("用户名")
  private String username;

  @ApiModelProperty("昵称")
  private String nickname;

  @ApiModelProperty("角色列表")
  private List<RoleVO> roleList;
}
