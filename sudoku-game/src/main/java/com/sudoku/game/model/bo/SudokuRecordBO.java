package com.sudoku.game.model.bo;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;

/**
 * 数独记录业务类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SudokuRecordBO implements Serializable {

  private static final long serialVersionUID = 2502730297414700117L;

  /**
   * 数独记录的ID
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
   * 数独难度ID
   */
  private Integer sudokuLevelId;

  /**
   * 是否记录
   */
  @Transient
  private boolean isRecord;
}
