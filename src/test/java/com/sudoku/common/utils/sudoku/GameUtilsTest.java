package com.sudoku.common.utils.sudoku;

import static org.junit.Assert.assertEquals;

import com.sudoku.common.constant.enums.AnswerSituation;
import com.sudoku.project.model.bo.SudokuDataBO;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import utils.SudokuDataUtils;

/**
 * 游戏工具类的测试类
 */
@RunWith(MockitoJUnitRunner.class)
public class GameUtilsTest {

  /**
   * 数独数据
   */
  private SudokuDataBO sudokuData;

  /**
   * 初始化测试数据
   */
  @Before
  public void setUp() {
    sudokuData = SudokuDataUtils.getSudokuData();
  }

  /**
   * 当回答与答案一致时，测试判断答题状态
   */
  @Test
  public void testJudgeAnswerSituationIfIdentical() {
    AnswerSituation situation = GameUtils.judgeAnswerSituation(SudokuDataUtils.getMatrixList(), sudokuData);
    assertEquals(AnswerSituation.IDENTICAL, situation);
  }

  /**
   * 当回答与答案正确但不一致时，测试判断答题状态
   */
  @Test
  public void testJudgeAnswerSituationIfCorrect() {
    AnswerSituation situation = GameUtils.judgeAnswerSituation(SudokuDataUtils.getCorrectMatrixList(), sudokuData);
    assertEquals(AnswerSituation.CORRECT, situation);
  }

  /**
   * 当回答有误时，测试判断答题状态
   */
  @Test
  public void testJudgeAnswerSituationIfError() {
    List<List<Integer>> userMatrix = SudokuDataUtils.getMatrixList();
    userMatrix.get(8).set(8, 1);

    AnswerSituation situation = GameUtils.judgeAnswerSituation(userMatrix, sudokuData);
    assertEquals(AnswerSituation.ERROR, situation);
  }

  /**
   * 当回答中存在null时，测试判断答题状态
   */
  @Test
  public void testJudgeAnswerSituationHasNull() {
    List<List<Integer>> userMatrix = SudokuDataUtils.getMatrixList();
    userMatrix.get(8).set(8, null);

    AnswerSituation situation = GameUtils.judgeAnswerSituation(userMatrix, sudokuData);
    assertEquals(AnswerSituation.ERROR, situation);
  }
}