package com.sudoku.game.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("普通游戏记录显示类")
public class NormalGameRecordVO implements Serializable {

  private static final long serialVersionUID = -6703335263096976938L;

  @ApiModelProperty("用户输入的数独矩阵")
  private int[][] inputMatrix;

  @ApiModelProperty("回答情况")
  private Integer answerSituation;

  @ApiModelProperty("数独记录")
  private SudokuRecordVO sudokuRecord;
}
