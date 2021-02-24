package com.sudoku.game.utils;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import com.sudoku.game.mapper.SudokuLevelMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * 数独等级工具类的测试类
 */
@RunWith(MockitoJUnitRunner.class)
public class SudokuLevelUtilsTest {

  @Mock
  private SudokuLevelMapper sudokuLevelMapper;

  private SudokuLevelUtils sudokuLevelUtils;

  /**
   * 数独等级列表
   */
  private List<Integer> sudokuLevelIdList;

  /**
   * 初始化测试数据
   */
  @Before
  public void setUp() {
    sudokuLevelUtils = new SudokuLevelUtils(sudokuLevelMapper);

    sudokuLevelIdList = new ArrayList<>();
    IntStream.range(0, 10).forEach(i -> sudokuLevelIdList.add(i));
    when(sudokuLevelMapper.selectId()).thenReturn(sudokuLevelIdList);
  }

  /**
   * 测试查找缺少的数独等级ID列表
   */
  @Test
  public void testFindLackId() {
    List<Integer> sudokuLevelIds = sudokuLevelIdList.subList(0, 5);
    List<Integer> lackLevelIds = sudokuLevelIdList.subList(5, 10);

    List<Integer> lackId = sudokuLevelUtils.findLackId(sudokuLevelIds);
    assertEquals(lackLevelIds, lackId);
  }

  /**
   * 测试查找空ID列表的缺少的数独等级ID列表
   */
  @Test
  public void testFindLackIdWithEmpty() {
    assertEquals(sudokuLevelIdList, sudokuLevelUtils.findLackId(null));
    assertEquals(sudokuLevelIdList, sudokuLevelUtils.findLackId(new ArrayList<>()));
  }
}