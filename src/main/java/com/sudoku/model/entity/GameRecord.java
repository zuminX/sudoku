package com.sudoku.model.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 游戏记录表对应的实体类
 */
@Data
public class GameRecord implements Serializable {

  private static final long serialVersionUID = -7408809637745812570L;
  /**
   * 游戏记录的ID
   */
  private Integer id;

  /**
   * 数独矩阵
   */
  private String sudokuMatrix;

  /**
   * 数独空缺
   */
  private String sudokuHoles;

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