package com.sudoku.utils;

import com.sudoku.model.dto.SudokuDataDTO;
import java.util.stream.IntStream;

/**
 * 数独游戏的工具类
 */
public class SudokuUtils {

  /**
   * 生成数独题目
   *
   * @param sudokuDataDTO 终盘的数独数据
   * @return 题目的数独数据
   */
  public static SudokuDataDTO generateSudokuTopic(SudokuDataDTO sudokuDataDTO) {
    //获取数独终盘的克隆对象
    SudokuDataDTO sudokuDataDTOClone = sudokuDataDTO.getClone();
    int[][] holes = sudokuDataDTOClone.getHoles();
    int[][] matrix = sudokuDataDTOClone.getMatrix();
    for (int i = 0; i < 9; i++) {
      for (int j = 0; j < 9; j++) {
        //将挖空的格子的数字设置为0
        if (holes[i][j] == 1) {
          matrix[i][j] = 0;
        }
      }
    }
    return sudokuDataDTOClone;
  }

  /**
   * 检查数独数据的合法性
   *
   * @param sudokuDataDTO 数独数据
   * @return 合法返回true，非法返回false
   */
  public static boolean checkSudokuValidity(SudokuDataDTO sudokuDataDTO) {
    int[][] matrix = sudokuDataDTO.getMatrix();
    //依次检查每个单元格的数字是否在1~9之间，及该数字在每行每列每块是否唯一
    return IntStream.range(0, 9)
        .noneMatch(i -> IntStream.range(0, 9)
            .anyMatch(j -> matrix[i][j] < 1 || matrix[i][j] > 9 || !isOnly(sudokuDataDTO, i, j)));
  }

  /**
   * 检查该数字在该数独中的行、列、块是否唯一
   *
   * @param sudokuDataDTO 数独数据
   * @param i          行
   * @param j          列
   * @return 唯一返回true, 不唯一返回false
   */
  public static boolean isOnly(SudokuDataDTO sudokuDataDTO, int i, int j) {
    int[][] matrix = sudokuDataDTO.getMatrix();
    //检查每行、列、块中该数字是否唯一
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

}
