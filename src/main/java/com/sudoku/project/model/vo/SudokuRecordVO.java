package com.sudoku.project.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("数独记录显示类")
public class SudokuRecordVO implements Serializable {

  private static final long serialVersionUID = -8702582802013644392L;

  @ApiModelProperty("数独矩阵字符串")
  private int[][] sudokuMatrix;

  @ApiModelProperty("数独格子字符串")
  private boolean[][] sudokuHoles;

  @ApiModelProperty("开始时间")
  private LocalDateTime startTime;

  @ApiModelProperty("结束时间")
  private LocalDateTime endTime;

  @ApiModelProperty("数独难度名")
  private String sudokuLevelName;
}
