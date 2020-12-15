package com.sudoku.common.utils.sudoku;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * 数独游戏工具类的测试类
 */
@RunWith(MockitoJUnitRunner.class)
public class SudokuUtilsTest {

  private int[][] matrix;

  /**
   * 初始化测试数据
   */
  @Before
  public void setUp() {
    matrix = new int[][]{
        {4, 3, 8, 7, 9, 6, 2, 1, 5},
        {6, 5, 9, 1, 3, 2, 4, 7, 8},
        {2, 7, 1, 4, 5, 8, 6, 9, 3},
        {8, 4, 5, 2, 1, 9, 3, 6, 7},
        {7, 1, 3, 5, 6, 4, 8, 2, 9},
        {9, 2, 6, 8, 7, 3, 1, 5, 4},
        {1, 9, 4, 3, 2, 5, 7, 8, 6},
        {3, 6, 2, 9, 8, 7, 5, 4, 1},
        {5, 8, 7, 6, 4, 1, 9, 3, 2}};
  }

  /**
   * 使用合法的数独矩阵测试检查数独数据的合法性
   */
  @Test
  public void testCheckSudokuValidityWithLegalMatrix() {
    assertTrue(SudokuUtils.checkSudokuValidity(matrix));
  }

  /**
   * 使用非法的数独矩阵测试检查数独数据的合法性
   */
  @Test
  public void testCheckSudokuValidityWithIllegalMatrix() {
    matrix[3][0] = 1;
    assertFalse(SudokuUtils.checkSudokuValidity(matrix));
  }

  /**
   * 使用唯一的数据测试检查数字在数独中的行、列、块是否唯一
   */
  @Test
  public void testIsOnlyWithOnlyData() {
    assertTrue(SudokuUtils.isOnly(matrix, 5, 1));
  }

  /**
   * 使用不唯一的数据测试检查数字在数独中的行、列、块是否唯一
   */
  @Test
  public void testIsOnlyWithNotOnlyData() {
    matrix[5][1] = 1;
    assertFalse(SudokuUtils.isOnly(matrix, 5, 1));
  }

  /**
   * 使用唯一的数据测试检查指定数在指定行中是否唯一
   */
  @Test
  public void testCheckRowIsOnlyWithOnlyData() {
    assertTrue(SudokuUtils.checkRowIsOnly(matrix[1], 2, 9));
  }

  /**
   * 使用不唯一的数据测试检查指定数在指定行中是否唯一
   */
  @Test
  public void testCheckRowIsOnlyWithNotOnlyData() {
    matrix[1][2] = 1;
    assertFalse(SudokuUtils.checkRowIsOnly(matrix[1], 2, 9));
  }

  /**
   * 使用唯一的数据测试检查指定数在指定列中是否唯一
   */
  @Test
  public void testCheckColumnIsOnlyWithOnlyData() {
    assertTrue(SudokuUtils.checkColumnIsOnly(matrix, 1, 2, 9));
  }

  /**
   * 使用不唯一的数据测试检查指定数在指定列中是否唯一
   */
  @Test
  public void testCheckColumnIsOnlyWithNotOnlyData() {
    matrix[1][2] = 1;
    assertFalse(SudokuUtils.checkColumnIsOnly(matrix, 1, 2, 9));
  }

  /**
   * 使用唯一的数据测试检查指定数在指定区块中是否唯一
   */
  @Test
  public void testCheckBlockIsOnlyWithOnlyData() {
    assertTrue(SudokuUtils.checkColumnIsOnly(matrix, 2, 2, 9));
  }

  /**
   * 使用不唯一的数据测试检查指定数在指定区块中是否唯一
   */
  @Test
  public void testCheckBlockIsOnlyWithNotOnlyData() {
    matrix[1][1] = 1;
    assertTrue(SudokuUtils.checkColumnIsOnly(matrix, 2, 2, 9));
  }

  /**
   * 测试将数独矩阵字符串还原为题目空缺数组
   */
  @Test
  public void unzipToMatrix() {
    String sudokuMatrix = "438796215659132478271458693845219367713564829926873154194325786362987541587641932";
    assertArrayEquals(matrix, SudokuUtils.unzipToMatrix(sudokuMatrix));
  }

  /**
   * 测试将题目空缺数组字符串还原为题目空缺数组
   */
  @Test
  public void unzipToHoles() {
    boolean[][] holes = {
        {true, true, true, true, true, false, true, true, true},
        {true, false, false, true, true, true, true, true, false},
        {false, true, true, true, true, false, true, true, true},
        {true, false, false, true, true, true, true, true, true},
        {true, true, false, true, true, true, true, true, true},
        {true, true, false, true, true, false, true, false, false},
        {true, true, true, false, false, false, true, true, true},
        {true, true, true, true, true, true, true, true, true},
        {true, true, true, true, true, true, true, true, true}};
    String sudokuHoles = "111110111100111110011110111100111111110111111110110100111000111111111111111111111";

    assertArrayEquals(holes, SudokuUtils.unzipToHoles(sudokuHoles));
  }
}