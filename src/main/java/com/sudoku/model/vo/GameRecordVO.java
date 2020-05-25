package com.sudoku.model.vo;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 游戏记录显示层
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class GameRecordVO implements Serializable {

  private static final long serialVersionUID = -1976268450582740297L;
  /**
   * 数独矩阵
   */
  private String sudokuMatrix;

  /**
   * 数独空缺
   */
  private String sudokuHoles;

  /**
   * 开始时间
   */
  private String startTime;

  /**
   * 结束时间
   */
  private String endTime;

  /**
   * 回答是否正确
   */
  private Boolean correct;

  /**
   * 数独难度名
   */
  private String sudokuLevelName;
}