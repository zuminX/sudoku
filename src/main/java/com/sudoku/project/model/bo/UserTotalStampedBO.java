package com.sudoku.project.model.bo;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 带更新时间的用户总数类
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserTotalStampedBO extends LocalDateStamped {

  /**
   * 用户总数
   */
  private Integer userTotal;

  public UserTotalStampedBO(Integer userTotal, LocalDate updateDate) {
    super(updateDate);
    this.userTotal = userTotal;
  }

}
