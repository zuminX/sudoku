package com.sudoku.model.dto;

import com.sudoku.constant.enums.AnswerSituation;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 用户答题情况传输层
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SubmitSudokuInformationDTO implements Serializable {

  private static final long serialVersionUID = 6641560930277580060L;
  /**
   * 结果情况
   */
  private AnswerSituation situation;
  /**
   * 数独矩阵
   */
  private int[][] matrix;
  /**
   * 花费的时间(ms)
   */
  private Long spendTime;
}
