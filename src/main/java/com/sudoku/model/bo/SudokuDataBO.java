package com.sudoku.model.bo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sudoku.utils.PublicUtils;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 数独数据传输层
 */
@AllArgsConstructor
@Getter
@Setter
public class SudokuDataBO implements Serializable {

  private static final long serialVersionUID = -2898567509394664887L;
  /**
   * 数独矩阵
   */
  private int[][] matrix;
  /**
   * 题目空缺数组
   */
  private int[][] holes;

  /**
   * 无参构造方法
   */
  public SudokuDataBO() {
    matrix = new int[9][9];
    holes = new int[9][9];
  }

  /**
   * 复制数独数据
   *
   * @return 克隆数独数据
   */
  @JsonIgnore
  public SudokuDataBO getClone() {
    //复制成员变量
    int[][] cloneMatrix = PublicUtils.getClone(this.matrix);
    int[][] cloneHoles = PublicUtils.getClone(this.holes);
    //返回复制后的数独数据
    return new SudokuDataBO(cloneMatrix, cloneHoles);
  }
}
