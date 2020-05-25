package com.sudoku.model.vo;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserGameInformationVO implements Serializable {

  private static final long serialVersionUID = -5952265855843549321L;
  /**
   * 提交的次数
   */
  private Integer total;

  /**
   * 提交正确的次数
   */
  private Integer correctNumber;

  /**
   * 平均用时
   */
  private Integer averageSpendTime;

  /**
   * 最短用时
   */
  private Integer minSpendTime;

  /**
   * 最长用时
   */
  private Integer maxSpendTime;

  /**
   * 数独等级名
   */
  private String sudokuLevelName;
}
