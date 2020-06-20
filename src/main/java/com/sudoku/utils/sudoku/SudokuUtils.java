package com.sudoku.utils.sudoku;

import com.sudoku.model.bo.SudokuDataBO;
import java.util.stream.IntStream;

/**
 * 数独游戏的工具类
 */
public class SudokuUtils {

  /**
   * 生成数独题目
   *
   * @param sudokuDataBO 终盘的数独数据
   * @return 题目的数独数据
   */
  public static SudokuDataBO generateSudokuTopic(SudokuDataBO sudokuDataBO) {
    //获取数独终盘的克隆对象
    SudokuDataBO sudokuDataBOClone = sudokuDataBO.getClone();
    int[][] holes = sudokuDataBOClone.getHoles();
    int[][] matrix = sudokuDataBOClone.getMatrix();
    for (int i = 0; i < 9; i++) {
      for (int j = 0; j < 9; j++) {
        if (isHole(holes, i, j)) {
          matrix[i][j] = 0;
        }
      }
    }
    return sudokuDataBOClone;
  }

  /**
   * 检查数独数据的合法性
   *
   * @param matrix 数独矩阵
   * @return 合法返回true，非法返回false
   */
  public static boolean checkSudokuValidity(int[][] matrix) {
    //依次检查每个单元格的数字是否在1~9之间，及该数字在每行每列每块是否唯一
    return IntStream.range(0, 9)
        .noneMatch(i -> IntStream.range(0, 9)
            .anyMatch(j -> matrix[i][j] < 1 || matrix[i][j] > 9 || !isOnly(matrix, i, j)));
  }

  /**
   * 检查该数字在该数独中的行、列、块是否唯一
   *
   * @param matrix 数独矩阵
   * @param i      行
   * @param j      列
   * @return 唯一返回true, 不唯一返回false
   */
  public static boolean isOnly(int[][] matrix, int i, int j) {
    return checkRowIsOnly(matrix[i], j, 9) && checkColumnIsOnly(matrix, i, j, 9) && checkBlockIsOnly(matrix, i, j);
  }


  /**
   * 检查该行的数字是否唯一
   *
   * @param matrix 该行的数独矩阵
   * @param j      列
   * @param size   检查个数
   * @return 唯一返回true, 不唯一返回false
   */
  public static boolean checkRowIsOnly(int[] matrix, int j, int size) {
    return IntStream.range(0, size).noneMatch(col -> col != j && matrix[col] == matrix[j]);
  }

  /**
   * 检查该列的数字是否唯一
   *
   * @param matrix 数独矩阵
   * @param i      行
   * @param j      列
   * @param size   检查个数
   * @return 唯一返回true, 不唯一返回false
   */
  public static boolean checkColumnIsOnly(int[][] matrix, int i, int j, int size) {
    return IntStream.range(0, size).noneMatch(row -> row != i && matrix[row][j] == matrix[i][j]);
  }

  /**
   * 检查该区块中的数字是否唯一
   *
   * @param matrix 数独矩阵
   * @param i      行
   * @param j      列
   * @return 唯一返回true, 不唯一返回false
   */
  public static boolean checkBlockIsOnly(int[][] matrix, int i, int j) {
    return IntStream.range((i / 3) * 3, ((i / 3) + 1) * 3)
        .noneMatch(row -> IntStream.range((j / 3) * 3, ((j / 3) + 1) * 3)
            .anyMatch(col -> row != i && col != j && matrix[row][col] == matrix[i][j]));
  }

  public static boolean isHole(int[][] holes, int i, int j) {
    return holes[i][j] == 1;
  }

  public static boolean isNotHole(int[][] holes, int i, int j) {
    return !isHole(holes, i, j);
  }

}
