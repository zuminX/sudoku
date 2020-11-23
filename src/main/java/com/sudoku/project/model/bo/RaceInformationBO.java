package com.sudoku.project.model.bo;


import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 竞赛信息业务类
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RaceInformationBO implements Serializable {


  private static final long serialVersionUID = 7276688338494779892L;

  /**
   * 数独数据
   */
  private SudokuDataBO sudokuData;

  /**
   * 开始时间
   */
  private LocalDateTime startTime;

  /**
   * 结束时间
   */
  private LocalDateTime endTime;
}
