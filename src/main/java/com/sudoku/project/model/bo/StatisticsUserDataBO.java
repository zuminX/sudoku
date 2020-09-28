package com.sudoku.project.model.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("用户统计数据类")
public class StatisticsUserDataBO {

  @ApiModelProperty("新增用户总数")
  private Integer newUserTotal;
  @ApiModelProperty("活跃用户总数")
  private Integer activeUserTotal;

  /**
   * 获取统计数据为零的对象
   *
   * @return 统计数据为零的对象
   */
  public static StatisticsUserDataBO getZero() {
    return new StatisticsUserDataBO(0, 0);
  }
}
