package com.sudoku.project.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel("数独等级显示类")
public class SudokuLevelVO implements Serializable {


  private static final long serialVersionUID = 6094658649761954674L;

  @ApiModelProperty("难度级别")
  private Integer level;

  @ApiModelProperty("难度名")
  private String name;
}
