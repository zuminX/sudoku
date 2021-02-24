package com.sudoku.game.model.result;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 查询历史普通游戏记录的结果
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NormalGameRecordResultForHistory implements Serializable {

  private static final long serialVersionUID = -4010493074200361619L;

  /**
   * 输入的数独矩阵
   */
  private String inputMatrix;

  /**
   * 回答情况
   */
  private Integer answerSituation;

  /**
   * 查询历史数独记录的结果
   */
  private SudokuRecordResultForHistory sudokuRecordResult;
}
