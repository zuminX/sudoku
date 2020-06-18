package com.sudoku.model.vo;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 数独等级的显示层
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SudokuLevelVO implements Serializable {

  private static final long serialVersionUID = 6094658649761954674L;
  /**
   * 难度级别
   */
  private Integer level;

  /**
   * 难度名
   */
  private String name;
}
