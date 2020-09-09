package com.sudoku.model.bo;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;

/**
 * 游戏记录业务类
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class GameRecordBO implements Serializable {

  private static final long serialVersionUID = -7078782494135605417L;

  /**
   * 游戏记录的ID
   */
  private Integer id;

  /**
   * 数独数据
   */
  private SudokuDataBO sudokuDataBO;

  /**
   * 开始时间
   */
  private Date startTime;

  /**
   * 结束时间
   */
  private Date endTime;

  /**
   * 回答是否正确
   */
  private Boolean correct;

  /**
   * 数独难度ID
   */
  private Integer sudokuLevelId;

  /**
   * 用户ID
   */
  private Integer userId;

  /**
   * 是否记录
   */
  @Transient
  private boolean isRecord;

  /**
   * 初始化构造器
   *
   * @return 游戏记录业务对象构造器
   */
  public static GameRecordBOBuilder initBuilder() {
    return GameRecordBO.builder()
        .startTime(new Date())
        .correct(false);
  }

}
