package com.sudoku.project.model.bo;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 时间戳类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocalDateStamped {

  /**
   * 数据更新的日期
   */
  private LocalDate updateDate;
}
