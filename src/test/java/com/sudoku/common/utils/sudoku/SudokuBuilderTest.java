package com.sudoku.common.utils.sudoku;

import static cn.hutool.core.lang.Assert.checkBetween;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.sudoku.project.model.bo.SudokuDataBO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * 数独游戏构建类的测试类
 */
@RunWith(MockitoJUnitRunner.class)
public class SudokuBuilderTest {

  private int minEmpty;

  private int maxEmpty;

  /**
   * 初始化测试数据
   */
  @Before
  public void setUp() {
    minEmpty = 30;
    maxEmpty = 35;
  }

  /**
   * 测试生成数独终盘
   */
  @Test
  public void testGenerateSudokuFinal() {
    SudokuDataBO sudokuData = SudokuBuilder.generateSudokuFinal(minEmpty, maxEmpty);
    int[][] matrix = sudokuData.getMatrix();

    assertNotNull(matrix);
    assertEquals(9, matrix.length);
    assertEquals(9, matrix[0].length);
    assertTrue(SudokuUtils.checkSudokuValidity(matrix));

    checkBetween(countHoleNumber(sudokuData.getHoles()), minEmpty, maxEmpty);
  }

  /**
   * 计算题目空缺数组中的空缺个数
   * @param holes 题目空缺数组
   * @return 空缺个数
   */
  private int countHoleNumber(boolean[][] holes) {
    int count = 0;
    for (boolean[] hole : holes) {
      for (boolean b : hole) {
        if (b) {
          ++count;
        }
      }
    }
    return count;
  }
}