package com.sudoku.model.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * 数独等级表对应的实体类
 */
@Data
public class SudokuLevel implements Serializable {

  private static final long serialVersionUID = 6464654109174415660L;
  /**
   * 数独难度ID
   */
  private Integer id;

  /**
   * 难度级别
   */
  private Integer level;

  /**
   * 难度名
   */
  private String name;

  /**
   * 最小的空缺格子数
   */
  private Integer minEmpty;

  /**
   * 最大的空缺格子数
   */
  private Integer maxEmpty;
}