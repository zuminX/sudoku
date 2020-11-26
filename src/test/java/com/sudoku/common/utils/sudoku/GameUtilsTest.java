package com.sudoku.common.utils.sudoku;

import static org.junit.Assert.assertEquals;

import com.sudoku.common.constant.enums.AnswerSituation;
import com.sudoku.common.tools.RedisUtils;
import com.sudoku.project.model.bo.SudokuDataBO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * 游戏工具类的测试类
 */
@RunWith(MockitoJUnitRunner.class)
public class GameUtilsTest {

  @Mock
  private RedisUtils redisUtils;

  private GameUtils gameUtils;

  /**
   * 数独数据
   */
  private SudokuDataBO sudokuData;

  /**
   * 初始化测试数据
   */
  @Before
  public void setUp() {
    gameUtils = new GameUtils(redisUtils);

    int[][] matrix = {
        {4, 3, 8, 7, 9, 6, 2, 1, 5},
        {6, 5, 9, 1, 3, 2, 4, 7, 8},
        {2, 7, 1, 4, 5, 8, 6, 9, 3},
        {8, 4, 5, 2, 1, 9, 3, 6, 7},
        {7, 1, 3, 5, 6, 4, 8, 2, 9},
        {9, 2, 6, 8, 7, 3, 1, 5, 4},
        {1, 9, 4, 3, 2, 5, 7, 8, 6},
        {3, 6, 2, 9, 8, 7, 5, 4, 1},
        {5, 8, 7, 6, 4, 1, 9, 3, 2}};
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
    sudokuData = new SudokuDataBO(matrix, holes);
  }

  /**
   * 测试回答情况与答案一致的情况
   */
  @Test
  public void testJudgeAnswerSituationIfIdentical() {
    List<List<Integer>> userMatrix = new ArrayList<>();
    userMatrix.add(List.of(4, 3, 8, 7, 9, 6, 2, 1, 5));
    userMatrix.add(List.of(6, 5, 9, 1, 3, 2, 4, 7, 8));
    userMatrix.add(List.of(2, 7, 1, 4, 5, 8, 6, 9, 3));
    userMatrix.add(List.of(8, 4, 5, 2, 1, 9, 3, 6, 7));
    userMatrix.add(List.of(7, 1, 3, 5, 6, 4, 8, 2, 9));
    userMatrix.add(List.of(9, 2, 6, 8, 7, 3, 1, 5, 4));
    userMatrix.add(List.of(1, 9, 4, 3, 2, 5, 7, 8, 6));
    userMatrix.add(List.of(3, 6, 2, 9, 8, 7, 5, 4, 1));
    userMatrix.add(List.of(5, 8, 7, 6, 4, 1, 9, 3, 2));

    AnswerSituation situation = gameUtils.judgeAnswerSituation(userMatrix, sudokuData);
    assertEquals(AnswerSituation.IDENTICAL, situation);
  }

  /**
   * 测试回答情况与答案不一致但正确的情况
   */
  @Test
  public void testJudgeAnswerSituationIfCorrect() {
    List<List<Integer>> userMatrix = new ArrayList<>();
    userMatrix.add(List.of(1, 3, 8, 2, 4, 6, 5, 7, 9));
    userMatrix.add(List.of(6, 5, 9, 1, 3, 7, 2, 4, 8));
    userMatrix.add(List.of(2, 7, 4, 5, 9, 8, 1, 6, 3));
    userMatrix.add(List.of(7, 4, 5, 6, 8, 2, 3, 9, 1));
    userMatrix.add(List.of(8, 1, 3, 4, 5, 9, 6, 2, 7));
    userMatrix.add(List.of(9, 2, 6, 7, 1, 3, 8, 5, 4));
    userMatrix.add(List.of(4, 8, 7, 3, 2, 5, 9, 1, 6));
    userMatrix.add(List.of(3, 6, 2, 9, 7, 1, 4, 8, 5));
    userMatrix.add(List.of(5, 9, 1, 8, 6, 4, 7, 3, 2));

    AnswerSituation situation = gameUtils.judgeAnswerSituation(userMatrix, sudokuData);
    assertEquals(AnswerSituation.CORRECT, situation);
  }

  /**
   * 测试回答错误的情况
   */
  @Test
  public void testJudgeAnswerSituationIfError() {
    List<List<Integer>> userMatrix = new ArrayList<>();
    userMatrix.add(List.of(4, 3, 8, 7, 9, 6, 1, 2, 5));
    userMatrix.add(List.of(6, 5, 9, 1, 3, 2, 4, 7, 8));
    userMatrix.add(List.of(2, 7, 1, 4, 5, 8, 6, 9, 3));
    userMatrix.add(List.of(8, 4, 5, 2, 1, 9, 3, 6, 7));
    userMatrix.add(List.of(7, 1, 3, 5, 6, 4, 8, 2, 9));
    userMatrix.add(List.of(9, 2, 6, 8, 7, 3, 1, 5, 4));
    userMatrix.add(List.of(1, 9, 4, 3, 2, 5, 7, 8, 6));
    userMatrix.add(List.of(3, 6, 2, 9, 8, 7, 5, 4, 1));
    userMatrix.add(List.of(5, 8, 7, 6, 4, 1, 9, 3, 2));

    AnswerSituation situation = gameUtils.judgeAnswerSituation(userMatrix, sudokuData);
    assertEquals(AnswerSituation.ERROR, situation);
  }

  /**
   * 测试用户的数独矩阵数据存在null的情况，应属于回答错误的情况
   */
  @Test
  public void testJudgeAnswerSituationHasNull() {
    List<List<Integer>> userMatrix = new ArrayList<>();
    userMatrix.add(Arrays.asList(4, 3, 8, 7, 9, 6, 2, 1, 5));
    userMatrix.add(Arrays.asList(6, 5, 9, 1, 3, 2, 4, 7, 8));
    userMatrix.add(Arrays.asList(2, 7, 1, 4, 5, 8, 6, 9, 3));
    userMatrix.add(Arrays.asList(8, 4, 5, 2, 1, 9, 3, 6, 7));
    userMatrix.add(Arrays.asList(7, 1, 3, 5, 6, 4, 8, 2, 9));
    userMatrix.add(Arrays.asList(9, 2, 6, 8, 7, 3, 1, 5, 4));
    userMatrix.add(Arrays.asList(1, 9, 4, 3, 2, 5, 7, 8, 6));
    userMatrix.add(Arrays.asList(3, 6, 2, 9, 8, 7, 5, 4, 1));
    userMatrix.add(Arrays.asList(5, 8, 7, 6, 4, 1, 9, 3, null));

    AnswerSituation situation = gameUtils.judgeAnswerSituation(userMatrix, sudokuData);
    assertEquals(AnswerSituation.ERROR, situation);
  }
}