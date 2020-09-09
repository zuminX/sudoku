package com.sudoku.model.bo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sudoku.utils.PublicUtils;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 数独数据业务类
 */
@AllArgsConstructor
@Data
public class SudokuDataBO implements Serializable {

  private static final long serialVersionUID = -2898567509394664887L;

  /**
   * 数独矩阵
   */
  private int[][] matrix;

  /**
   * 题目空缺数组
   */
  private boolean[][] holes;

  /**
   * 无参构造方法
   */
  public SudokuDataBO() {
    matrix = new int[9][9];
    holes = new boolean[9][9];
  }

  /**
   * 复制数独数据
   *
   * @return 克隆数独数据
   */
  @JsonIgnore
  public SudokuDataBO getClone() {
    int[][] cloneMatrix = PublicUtils.clone(this.matrix, int[].class);
    boolean[][] cloneHoles = PublicUtils.clone(this.holes, boolean[].class);
    return new SudokuDataBO(cloneMatrix, cloneHoles);
  }
}
