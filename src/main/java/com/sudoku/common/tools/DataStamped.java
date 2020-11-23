package com.sudoku.common.tools;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 带更新时间的数据类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataStamped<T> {

  /**
   * 数据
   */
  private T data;
  /**
   * 数据更新的日期
   */
  private LocalDate updateDate;

  public DataStamped(T data) {
    this.data = data;
    this.updateDate = LocalDate.now();
  }
}
