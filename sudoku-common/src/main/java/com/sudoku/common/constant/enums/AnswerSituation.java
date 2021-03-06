package com.sudoku.common.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 用户提交填写数独的结果情况类
 */
@Getter
@ToString
@AllArgsConstructor
public enum AnswerSituation {
  /**
   * 与答案一致
   */
  IDENTICAL(0),
  /**
   * 未完成
   */
  UNFINISHED(1),
  /**
   * 错误
   */
  ERROR(2);

  /**
   * 编号
   */
  private final int code;

  /**
   * 回答是否正确
   *
   * @return 正确返回true，错误返回false
   */
  public boolean isRight() {
    return this.equals(IDENTICAL);
  }
}
