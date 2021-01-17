package com.sudoku.project.model.bo;

import com.sudoku.common.utils.PublicUtils;
import com.sudoku.common.utils.sudoku.SudokuUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
@ApiModel("数独数据业务类")
public class SudokuDataBO implements Serializable {

  private static final long serialVersionUID = -2898567509394664887L;

  @ApiModelProperty("数独矩阵")
  private int[][] matrix;

  @ApiModelProperty("题目空缺数组")
  private boolean[][] holes;

  /**
   * 无参构造方法
   */
  public SudokuDataBO() {
    matrix = new int[9][9];
    holes = new boolean[9][9];
  }

  /**
   * 隐藏空缺的格子信息
   *
   * @return 隐藏后的数独数据对象
   */
  public SudokuDataBO hideVacancyGrid() {
    return new SudokuDataBO(SudokuUtils.setVacancyGridToZero(matrix, holes), PublicUtils.deepClone(holes));
  }
}
