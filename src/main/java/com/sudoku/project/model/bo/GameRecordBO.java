package com.sudoku.project.model.bo;

import java.io.Serializable;
import java.time.LocalDateTime;
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
  private LocalDateTime startTime;

  /**
   * 结束时间
   */
  private LocalDateTime endTime;

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
}
