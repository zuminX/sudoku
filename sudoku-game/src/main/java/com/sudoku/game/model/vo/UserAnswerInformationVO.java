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
@ApiModel("用户答题情况显示类")
public class UserAnswerInformationVO implements Serializable {

  private static final long serialVersionUID = 7015015635447903859L;

  @ApiModelProperty("结果情况")
  private int situation;

  @ApiModelProperty("数独矩阵")
  private int[][] matrix;

  @ApiModelProperty("花费的时间(ms)")
  private Long spendTime;
}
