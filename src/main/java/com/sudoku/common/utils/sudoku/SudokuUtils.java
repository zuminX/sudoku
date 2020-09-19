package com.sudoku.common.utils.sudoku;

import com.sudoku.project.model.bo.SudokuDataBO;
import java.util.stream.IntStream;

/**
 * 数独游戏的工具类
 */
public class SudokuUtils {

  /**
   * 私有构造方法，防止实例化
   */
  private SudokuUtils() {
  }

  /**
   * 生成数独题目
   *
   * @param sudokuData 终盘的数独数据
   * @return 题目的数独数据
   */
  public static SudokuDataBO generateSudokuTopic(SudokuDataBO sudokuData) {
    SudokuDataBO sudokuDataClone = sudokuData.getClone();
    boolean[][] holes = sudokuDataClone.getHoles();
    int[][] matrix = sudokuDataClone.getMatrix();
    for (int i = 0; i < 9; i++) {
      for (int j = 0; j < 9; j++) {
        if (isHole(holes, i, j)) {
          matrix[i][j] = 0;
        }
      }
    }
    return sudokuDataClone;
  }

  /**
   * 检查数独数据的合法性 依次检查每个单元格的数字是否在1~9之间，及该数字在每行每列每块是否唯一
   *
   * @param matrix 数独矩阵
   * @return 合法返回true，非法返回false
   */
  public static boolean checkSudokuValidity(int[][] matrix) {
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

  /**
   * 判断指定位置是否为空缺格子
   *
   * @param holes 题目空缺数组
   * @param i     行
   * @param j     列
   * @return 是空缺格子返回true，否则返回false
   */
  public static boolean isHole(boolean[][] holes, int i, int j) {
    return holes[i][j];
  }

  /**
   * 判断指定位置是否不为空缺格子
   *
   * @param holes 题目空缺数组
   * @param i     行
   * @param j     列
   * @return 不是空缺格子返回true，否则返回false
   */
  public static boolean isNotHole(boolean[][] holes, int i, int j) {
    return !isHole(holes, i, j);
  }

}
