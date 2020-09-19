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

  @ApiModelProperty("用户总数")
  private Integer userTotal;
  @ApiModelProperty("用户活跃数")
  private Integer userActiveTotal;
}
