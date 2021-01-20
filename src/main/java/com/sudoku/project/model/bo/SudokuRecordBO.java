package com.sudoku.project.model.bo;

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
public class SudokuRecordBO {

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
