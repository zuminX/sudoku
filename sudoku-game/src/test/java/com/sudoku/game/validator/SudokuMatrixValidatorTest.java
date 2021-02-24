package com.sudoku.game.validator;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * 数独矩阵验证器类的测试类
 */
@RunWith(Parameterized.class)
public class SudokuMatrixValidatorTest {

  private final List<List<Integer>> value;

  private final boolean expected;

  /**
   * 用于参数化的构造方法
   *
   * @param value    二维Integer型列表
   * @param expected 预期结果
   */
  public SudokuMatrixValidatorTest(List<List<Integer>> value, boolean expected) {
    this.value = value;
    this.expected = expected;
  }

  /**
   * 参数化数据
   *
   * @return 数据
   */
  @Parameters
  public static Collection<Object[]> boundaryValueData() {
    return Arrays.asList(new Object[][]{
        {null, false},
        {new ArrayList<>(), false},
        {generateTwoDimensionalList(9, 1), false},
        {generateTwoDimensionalList(9, 9), true},
    });
  }

  /**
   * 生成二维列表
   *
   * @param row    行
   * @param column 列
   * @return row*column的列表
   */
  private static List<List<Integer>> generateTwoDimensionalList(int row, int column) {
    List<List<Integer>> matrix = new ArrayList<>(row);
    for (int i = 0; i < row; i++) {
      matrix.add(new ArrayList<>(column));
      for (int j = 0; j < column; j++) {
        matrix.get(i).add(0);
      }
    }
    return matrix;
  }

  /**
   * 测试校验二维列表是否为数独矩阵
   */
  @Test
  public void testIsValid() {
    assertEquals(expected, SudokuMatrixValidator.isValid(value));
  }
}