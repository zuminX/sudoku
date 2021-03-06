package com.sudoku.game.utils.sudoku;

import com.sudoku.common.tools.functional.TriConsumer;
import com.sudoku.common.utils.PublicUtils;
import com.sudoku.common.utils.TwoDimensionalListUtils;
import java.util.List;
import java.util.stream.IntStream;

/**
 * 数独游戏工具类
 */
public class SudokuUtils {

  /**
   * 私有构造方法，防止实例化
   */
  private SudokuUtils() {
  }

  /**
   * 检查数独数据的合法性
   * <p>
   * 依次检查每个单元格的数字是否在1~9之间，及该数字在每行每列每块是否唯一
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
   * 检查数独数据的合法性
   *
   * @param matrix 数独矩阵
   * @return 合法返回true，非法返回false
   */
  public static boolean checkSudokuValidity(List<List<Integer>> matrix) {
    return checkSudokuValidity(PublicUtils.unwrapIntArray(matrix));
  }

  /**
   * 判断给定的数独题目是否只有唯一解
   *
   * @param matrix 数独矩阵
   * @param holes  题目空缺数组
   * @return 若解只有一个则返回true，若存在多解或无解则返回false
   */
  public static boolean hasOnlyOneSolution(int[][] matrix, boolean[][] holes) {
    return new SudokuSolver(setVacancyGridToZero(matrix, holes)).solutionCount() == 1;
  }

  /**
   * 判断给定的数独题目是否存在多解或无解
   *
   * @param matrix 数独矩阵
   * @param holes  题目空缺数组
   * @return 若存在多解或无解则返回true，若解只有一个则返回false
   */
  public static boolean hasNotOnlyOneSolution(int[][] matrix, boolean[][] holes) {
    return !hasOnlyOneSolution(matrix, holes);
  }

  /**
   * 返回数给定独矩阵的副本，并将其空缺的格子的值设置为零
   *
   * @param matrix 数独矩阵
   * @param holes  题目空缺数组
   * @return 空缺的格子为零的数独矩阵
   */
  public static int[][] setVacancyGridToZero(int[][] matrix, boolean[][] holes) {
    int[][] cloneMatrix = PublicUtils.deepClone(matrix);
    TriConsumer<Integer, Integer, Boolean> setMatrixToZero = (i, j, value) -> cloneMatrix[i][j] = 0;
    TwoDimensionalListUtils.forEach(PublicUtils.wrap(holes), setMatrixToZero.condition((i, j, value) -> value));
    return cloneMatrix;
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
   * 检查matrix[j]在该行中是否唯一
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
   * 检查matrix[i][j]在该列中是否唯一
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
   * 检查matrix[i][j]在该区块中是否唯一
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
   * 将字符串还原为数独矩阵数组
   *
   * @param sudokuMatrix 数独矩阵数组的字符串形式
   * @return 数独矩阵数组
   */
  public static int[][] unzipToMatrix(String sudokuMatrix) {
    int[][] matrix = new int[9][9];
    for (int i = 0, index = 0; i < 9; i++) {
      for (int j = 0; j < 9; j++) {
        matrix[i][j] = sudokuMatrix.charAt(index++) - '0';
      }
    }
    return matrix;
  }

  /**
   * 将字符串还原为题目空缺数组
   *
   * @param sudokuHoles 题目空缺数组的字符串形式
   * @return 题目空缺数组
   */
  public static boolean[][] unzipToHoles(String sudokuHoles) {
    boolean[][] holes = new boolean[9][9];
    for (int i = 0, index = 0; i < 9; i++) {
      for (int j = 0; j < 9; j++) {
        holes[i][j] = sudokuHoles.charAt(index++) == '1';
      }
    }
    return holes;
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
