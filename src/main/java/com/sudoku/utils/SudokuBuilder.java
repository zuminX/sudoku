package com.sudoku.utils;

import static com.sudoku.utils.PublicUtils.getRandomInt;
import static com.sudoku.utils.PublicUtils.randomizedArray;
import static com.sudoku.utils.SudokuUtils.checkColumnIsOnly;
import static com.sudoku.utils.SudokuUtils.checkRowIsOnly;

import com.sudoku.model.bo.SudokuDataBO;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 数独游戏的构建类
 */
public class SudokuBuilder {

  /**
   * 产生随机数组时的阈值
   */
  private final static int MAX_CALL_RANDOM_ARRAY_TIMES = 220;

  /**
   * 生成数独终盘
   *
   * @param minEmpty 最小的空缺格子数
   * @param maxEmpty 最大的空缺格子数
   * @return 数独数据
   */
  public static SudokuDataBO generateSudokuFinal(int minEmpty, int maxEmpty) {
    SudokuDataBO sudokuDataBO = new SudokuDataBO();
    //生成数独矩阵
    generatePuzzleMatrix(sudokuDataBO);
    //生成题目空缺数组
    digHolesByGameDifficulty(sudokuDataBO, minEmpty, maxEmpty);
    return sudokuDataBO;
  }

  /**
   * 生成数独矩阵
   *
   * @param sudokuDataBO 数独数据
   */
  private static void generatePuzzleMatrix(SudokuDataBO sudokuDataBO) {
    int[][] matrix = sudokuDataBO.getMatrix();
    //记录buildRandomArray()调用次数
    AtomicInteger currentTimes = new AtomicInteger(0);
    for (int row = 0; row < 9; row++) {
      //生成第0行数独矩阵
      if (row == 0) {
        currentTimes.set(0);
        matrix[0] = buildRandomArray(currentTimes);
        //生成其他行数独矩阵
      } else {
        int[] tempRandomArray = buildRandomArray(currentTimes);
        for (int col = 0; col < 9; col++) {
          //调用生成随机数组的次数未超过阈值
          if (currentTimes.get() < MAX_CALL_RANDOM_ARRAY_TIMES) {
            //将随机数组赋值给数独矩阵
            if (!isCandidateNmbFound(sudokuDataBO, tempRandomArray, row, col)) {
              //将该行的数据置为0
              clearSudokuMatrixRow(sudokuDataBO, row);
              //重新生成该行随机数组
              row -= 1;
              col = 8;
              tempRandomArray = buildRandomArray(currentTimes);
            }
            //调用生成随机数组的次数超过阈值
          } else {
            clearSudokuMatrix(sudokuDataBO);
            //重新生成数独矩阵
            row = -1;
            col = 8;
            currentTimes.set(0);
          }
        }
      }
    }
  }

  /**
   * 清空数独矩阵的行
   *
   * @param sudokuDataBO 数独数据
   * @param row        行
   */
  private static void clearSudokuMatrixRow(SudokuDataBO sudokuDataBO, int row) {
    int[] rowMatrix = sudokuDataBO.getMatrix()[row];
    //设置该行的数据为零
    Arrays.fill(rowMatrix, 0, 9, 0);
  }

  /**
   * 清空数独矩阵
   *
   * @param sudokuDataBO 数独数据
   */
  private static void clearSudokuMatrix(SudokuDataBO sudokuDataBO) {
    //清空所有行的数据
    for (int i = 0; i < 9; i++) {
      clearSudokuMatrixRow(sudokuDataBO, i);
    }
  }

  /**
   * 尝试给数独矩阵的第row行第col列的数据赋值
   *
   * @param sudokuDataBO  数独数据
   * @param randomArray 随机数组
   * @param row         行
   * @param col         列
   * @return 能赋值返回true，不能赋值返回false
   */
  private static boolean isCandidateNmbFound(SudokuDataBO sudokuDataBO, int[] randomArray, int row, int col) {
    int[][] matrix = sudokuDataBO.getMatrix();
    //遍历随机数组，尝试给matrix[row][col]赋值
    for (int i = 0; i < randomArray.length; i++) {
      if (randomArray[i] == -1) {
        continue;
      }
      int random = randomArray[i];
      matrix[row][col] = random;
      //放置该数是否与数独矩阵有冲突
      if (noConflict(sudokuDataBO, row, col)) {
        randomArray[i] = -1;
        return true;
      }
    }
    return false;
  }

  /**
   * 判断该行、该列的数在数独矩阵中是否存在冲突
   *
   * @param sudokuDataBO 数独数据
   * @param row        行
   * @param col        列
   * @return 存在冲突返回false，不存在冲突返回true
   */
  private static boolean noConflict(SudokuDataBO sudokuDataBO, int row, int col) {
    return noConflictInRow(sudokuDataBO, row, col) && noConflictInColumn(sudokuDataBO, row, col) &&
        noConflictInBlock(sudokuDataBO, row, col);
  }

  /**
   * 判断该行、该列的数在数独矩阵的行中是否存在冲突
   *
   * @param sudokuDataBO 数独数据
   * @param row        行
   * @param col        列
   * @return 存在冲突返回false，不存在冲突返回true
   */
  private static boolean noConflictInRow(SudokuDataBO sudokuDataBO, int row, int col) {
    return checkRowIsOnly(sudokuDataBO.getMatrix()[row], col, col);
  }

  /**
   * 判断该行、该列的数在数独矩阵的列中是否存在冲突
   *
   * @param sudokuDataBO 数独数据
   * @param row        行
   * @param col        列
   * @return 存在冲突返回false，不存在冲突返回true
   */
  private static boolean noConflictInColumn(SudokuDataBO sudokuDataBO, int row, int col) {
    return checkColumnIsOnly(sudokuDataBO.getMatrix(), row, col, row);
  }

  /**
   * 判断该行、该列的数在数独矩阵的宫中是否存在冲突
   *
   * @param sudokuDataBO 数独数据
   * @param row        行
   * @param col        列
   * @return 存在冲突返回false，不存在冲突返回true
   */
  private static boolean noConflictInBlock(SudokuDataBO sudokuDataBO, int row, int col) {
    return SudokuUtils.checkBlockIsOnly(sudokuDataBO.getMatrix(), row, col);
  }

  /**
   * 返回一个1-9的随机排列数组
   *
   * @param currentTimes 执行该方法的次数
   * @return 打乱的数组
   */
  private static int[] buildRandomArray(AtomicInteger currentTimes) {
    //执行该方法的次数加一
    currentTimes.incrementAndGet();
    int[] array = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
    //利用Knuth洗牌算法打乱数组
    randomizedArray(array);
    return array;
  }

  /**
   * 根据空缺格子数生成对应题目的空缺数组
   *
   * @param sudokuDataBO 数独数据
   * @param minEmpty   最小的空缺格子数
   * @param maxEmpty   最大的空缺格子数
   */
  private static void digHolesByGameDifficulty(SudokuDataBO sudokuDataBO, int minEmpty, int maxEmpty) {
    int[][] holes = sudokuDataBO.getHoles();
    //设置为1视为空缺格子
    for (int i = 0, random = getRandomInt(minEmpty, maxEmpty); i < random; i++) {
      holes[i / 9][i % 9] = 1;
    }
    //利用Knuth洗牌算法打乱数组
    randomizedArray(holes);
  }

}
