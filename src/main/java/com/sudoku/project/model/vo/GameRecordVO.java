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
@ApiModel("游戏记录显示类")
public class GameRecordVO implements Serializable {


  private static final long serialVersionUID = -1976268450582740297L;

  @ApiModelProperty("数独矩阵字符串")
  private String sudokuMatrix;

  @ApiModelProperty("数独格子字符串")
  private String sudokuHoles;

  @ApiModelProperty("开始时间")
  private String startTime;

  @ApiModelProperty("结束时间")
  private String endTime;

  @ApiModelProperty("回答是否正确")
  private Boolean correct;

  @ApiModelProperty("数独难度名")
  private String sudokuLevelName;
}