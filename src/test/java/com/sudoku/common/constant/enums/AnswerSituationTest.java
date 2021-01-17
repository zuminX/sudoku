package com.sudoku.common.constant.enums;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * 用户提交填写数独的结果情况类的测试类
 */
@RunWith(MockitoJUnitRunner.class)
public class AnswerSituationTest {

  /**
   * 当结果为IDENTICAL时，回答应为正确
   */
  @Test
  public void testIsRightWithCorrect() {
    assertTrue(AnswerSituation.IDENTICAL.isRight());
  }

  /**
   * 当结果为UNFINISHED时，回答应为错误
   */
  @Test
  public void testIsRightWithIdentical() {
    assertFalse(AnswerSituation.UNFINISHED.isRight());
  }

  /**
   * 当结果为ERROR时，回答应为错误
   */
  @Test
  public void testIsRightWithError() {
    assertFalse(AnswerSituation.ERROR.isRight());
  }

}