package com.sudoku.common.validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * 数独矩阵验证器类的测试类
 */
@RunWith(MockitoJUnitRunner.class)
public class SudokuMatrixValidatorTest {

  /**
   * 测试校验二维列表是否为数独矩阵
   */
  @Test
  public void testIsValid() {
    assertFalse(SudokuMatrixValidator.isValid(generateTwoDimensionalList(3, 3)));
    assertFalse(SudokuMatrixValidator.isValid(generateTwoDimensionalList(9, 4)));
    assertTrue(SudokuMatrixValidator.isValid(generateTwoDimensionalList(9, 9)));
  }

  /**
   * 测试校验空的二维列表是否为数独矩阵
   */
  @Test
  public void testIsValidWithEmpty() {
    assertFalse(SudokuMatrixValidator.isValid(null));
    assertFalse(SudokuMatrixValidator.isValid(new ArrayList<>()));
  }

  /**
   * 生成二维列表
   *
   * @param row 行
   * @param column 列
   * @return row*column的列表
   */
  private List<List<Integer>> generateTwoDimensionalList(int row, int column) {
    List<List<Integer>> matrix = new ArrayList<>(row);
    for (int i = 0; i < row; i++) {
      matrix.add(new ArrayList<>(column));
      for (int j = 0; j < column; j++) {
        matrix.get(i).add(j);
      }
    }
    return matrix;
  }
}