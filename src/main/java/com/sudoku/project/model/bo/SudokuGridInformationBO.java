package com.sudoku.project.model.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel("数独格子信息业务类")
public class SudokuGridInformationBO implements Serializable {

  private static final long serialVersionUID = -4914426979421357329L;

  @ApiModelProperty("行数")
  private int row;

  @ApiModelProperty("列数")
  private int column;

  @ApiModelProperty("对应格子的数值")
  private int value;
}
