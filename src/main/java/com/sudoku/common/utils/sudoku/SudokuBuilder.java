package com.sudoku.common.utils.sudoku;

import static com.sudoku.common.utils.PublicUtils.getRandomInt;
import static com.sudoku.common.utils.PublicUtils.randomizedArray;
import static com.sudoku.common.utils.sudoku.SudokuUtils.checkBlockIsOnly;
import static com.sudoku.common.utils.sudoku.SudokuUtils.checkColumnIsOnly;
import static com.sudoku.common.utils.sudoku.SudokuUtils.checkRowIsOnly;

import com.sudoku.project.model.bo.SudokuDataBO;
import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * 数独游戏构建类
 */
public class SudokuBuilder {

  /**
   * 产生随机数组时的阈值
   */
  private static final int MAX_CALL_RANDOM_ARRAY_TIMES = 220;

  /**
   * 私有构造方法，防止实例化
   */
  private SudokuBuilder() {
  }

  /**
   * 生成数独终盘
   *
   * @param minEmpty 最小的空缺格子数
   * @param maxEmpty 最大的空缺格子数
   * @return 数独数据
   */
  public static SudokuDataBO generateSudokuFinal(int minEmpty, int maxEmpty) {
    SudokuSolver sudokuSolver;
    SudokuDataBO sudokuData;
    do {
      sudokuData = new SudokuDataBO();
      generateMatrix(sudokuData.getMatrix());
      digHolesByGameDifficulty(sudokuData.getHoles(), minEmpty, maxEmpty);
      sudokuSolver = new SudokuSolver(sudokuData.hideVacancyGrid().getMatrix());
    } while (sudokuSolver.solutionCount() != 1);
    return sudokuData;
  }

  /**
   * 生成数独矩阵
   *
   * @param matrix 数独矩阵
   */
  private static void generateMatrix(int[][] matrix) {
    //记录buildRandomArray()调用次数
    int currentTimes = 0;
    GenerateStatus status = GenerateStatus.INIT_FIRST_ROW;
    int row = 0, col = 0;
    int[] tempRandomArray = null;
    while (row != 9) {
      switch (status) {
        case FILL_GRID:
          if (currentTimes >= MAX_CALL_RANDOM_ARRAY_TIMES) {
            status = GenerateStatus.EMPTY_ALL;
            break;
          }
          if (!isCandidateNmbFound(matrix, tempRandomArray, row, col)) {
            status = GenerateStatus.EMPTY_ROW;
            break;
          }
          if (++col == 9) {
            row++;
            status = GenerateStatus.FILL_ROW;
          }
          break;
        case EMPTY_ROW:
          clearMatrixRow(matrix, row);
          status = GenerateStatus.FILL_ROW;
          break;
        case FILL_ROW:
          tempRandomArray = buildRandomArray();
          currentTimes++;
          col = 0;
          status = GenerateStatus.FILL_GRID;
          break;
        case INIT_FIRST_ROW:
          matrix[0] = buildRandomArray();
          currentTimes = 1;
          row = 0;
          status = GenerateStatus.FILL_ROW;
          break;
        case EMPTY_ALL:
          clearMatrix(matrix);
          status = GenerateStatus.INIT_FIRST_ROW;
          break;
      }
    }
  }

  /**
   * 清空数独矩阵的行
   *
   * @param matrix 数独矩阵
   * @param row    行
   */
  private static void clearMatrixRow(int[][] matrix, int row) {
    Arrays.fill(matrix[row], 0, 9, 0);
  }

  /**
   * 清空数独矩阵
   *
   * @param matrix 数独矩阵
   */
  private static void clearMatrix(int[][] matrix) {
    IntStream.range(0, 9).forEach(i -> clearMatrixRow(matrix, i));
  }

  /**
   * 尝试给数独矩阵的第row行第col列的数据赋值
   *
   * @param matrix      数独矩阵
   * @param randomArray 随机数组
   * @param row         行
   * @param col         列
   * @return 能赋值返回true，不能赋值返回false
   */
  private static boolean isCandidateNmbFound(int[][] matrix, int[] randomArray, int row, int col) {
    for (int i = 0; i < randomArray.length; i++) {
      if (randomArray[i] == -1) {
        continue;
      }
      int random = randomArray[i];
      matrix[row][col] = random;
      if (noConflict(matrix, row, col)) {
        randomArray[i] = -1;
        return true;
      }
    }
    return false;
  }

  /**
   * 判断该行、该列的数在数独矩阵中是否存在冲突
   *
   * @param matrix 数独矩阵
   * @param row    行
   * @param col    列
   * @return 存在冲突返回false，不存在冲突返回true
   */
  private static boolean noConflict(int[][] matrix, int row, int col) {
    return checkRowIsOnly(matrix[row], col, col) && checkColumnIsOnly(matrix, row, col, row) && checkBlockIsOnly(matrix, row, col);
  }

  /**
   * 返回一个1-9的随机排列数组
   *
   * @return 打乱的数组
   */
  private static int[] buildRandomArray() {
    int[] array = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
    randomizedArray(array);
    return array;
  }

  /**
   * 根据空缺格子数生成对应题目的空缺数组
   *
   * @param holes    题目空缺数组
   * @param minEmpty 最小的空缺格子数
   * @param maxEmpty 最大的空缺格子数
   */
  private static void digHolesByGameDifficulty(boolean[][] holes, int minEmpty, int maxEmpty) {
    for (int i = getRandomInt(minEmpty, maxEmpty) - 1; i >= 0; i--) {
      holes[i / 9][i % 9] = true;
    }
    randomizedArray(holes);
  }

  /**
   * 生成数独矩阵算法的状态
   */
  private enum GenerateStatus {
    /**
     * 清空矩阵的所有数据
     */
    EMPTY_ALL,
    /**
     * 清空矩阵的行数据
     */
    EMPTY_ROW,
    /**
     * 填充矩阵的格子数据
     */
    FILL_GRID,
    /**
     * 填充矩阵的行数据
     */
    FILL_ROW,
    /**
     * 初始化矩阵的第一行数据
     */
    INIT_FIRST_ROW
  }

}
