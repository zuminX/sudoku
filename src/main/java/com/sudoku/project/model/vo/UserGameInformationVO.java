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
@ApiModel("用户游戏信息显示类")
public class UserGameInformationVO implements Serializable {

  private static final long serialVersionUID = -5952265855843549321L;

  @ApiModelProperty("提交的次数")
  private Integer total;

  @ApiModelProperty("提交正确的次数")
  private Integer correctNumber;

  @ApiModelProperty("平均用时")
  private Integer averageSpendTime;

  @ApiModelProperty("最短用时")
  private Integer minSpendTime;

  @ApiModelProperty("最长用时")
  private Integer maxSpendTime;

  @ApiModelProperty("数独等级名")
  private String sudokuLevelName;
}
