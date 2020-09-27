package com.sudoku.project.model.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("游戏统计数据类")
public class StatisticsGameDataBO {

  @ApiModelProperty("提交正确总数")
  private Integer correctTotal;
  @ApiModelProperty("提交错误总数")
  private Integer errorTotal;
  @ApiModelProperty("数独难度ID")
  private Integer sudokuLevelId;
}
