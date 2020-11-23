package com.sudoku.project.model.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("游戏统计数据类")
public class StatisticsGameDataBO implements Serializable {


  private static final long serialVersionUID = -8287602078144839683L;

  @ApiModelProperty("提交正确总数")
  private Integer correctTotal;

  @ApiModelProperty("提交错误总数")
  private Integer errorTotal;

  @ApiModelProperty("数独难度ID")
  private Integer sudokuLevelId;

  /**
   * 获取统计数据为零的对象
   *
   * @param sudokuLevelId 数独难度ID
   * @return 统计数据为零的对象
   */
  public static StatisticsGameDataBO getZero(Integer sudokuLevelId) {
    return new StatisticsGameDataBO(0, 0, sudokuLevelId);
  }
}
