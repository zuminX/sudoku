package com.sudoku.model.dto;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 游戏记录传输层
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class GameRecordDTO implements Serializable {

  private static final long serialVersionUID = -7078782494135605417L;
  /**
   * 游戏记录的ID
   */
  private Integer id;
  /**
   * 数独数据
   */
  private SudokuDataDTO sudokuDataDTO;
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
  private Integer slid;
  /**
   * 用户ID
   */
  private Integer uid;
}
