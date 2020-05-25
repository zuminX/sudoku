package com.sudoku.model.vo;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户答题情况显示层
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SubmitSudokuInformationVO implements Serializable {

  private static final long serialVersionUID = 7015015635447903859L;
  /**
   * 结果情况
   */
  private int situation;
  /**
   * 数独矩阵
   */
  private int[][] matrix;
  /**
   * 花费的时间(ms)
   */
  private Long spendTime;
}
