package com.sudoku.model.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 数独格子的信息
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SudokuGridInformationDTO implements Serializable {

  private static final long serialVersionUID = -4914426979421357329L;
  /**
   * 行数
   */
  private int row;
  /**
   * 列数
   */
  private int column;
  /**
   * 值
   */
  private int value;
}
