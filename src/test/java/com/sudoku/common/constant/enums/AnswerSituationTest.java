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
   * 与答案不一致但正确的状态的回答情况应是正确的
   */
  @Test
  public void testIsRightWithCorrect() {
    assertTrue(AnswerSituation.CORRECT.isRight());
  }

  /**
   * 与答案一致的状态的回答情况应是正确的
   */
  @Test
  public void testIsRightWithIdentical() {
    assertTrue(AnswerSituation.IDENTICAL.isRight());
  }

  /**
   * 错误的状态的回答情况应是错误的
   */
  @Test
  public void testIsRightWithError() {
    assertFalse(AnswerSituation.ERROR.isRight());
  }

}