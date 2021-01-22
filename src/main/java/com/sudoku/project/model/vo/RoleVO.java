package com.sudoku.project.model.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("角色显示层类")
public class RoleVO implements Serializable {

  private static final long serialVersionUID = 4031992534685852432L;

  @ApiModelProperty("角色名")
  private String name;

  @ApiModelProperty("角色名称")
  private String nameZh;
}
