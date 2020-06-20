package com.sudoku.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 数独等级表对应的实体类
 */
@Data
@TableName(value = "sudoku_level")
public class SudokuLevel implements Serializable {

  private static final long serialVersionUID = -187769059852176191L;
  /**
   * 数独难度ID
   */
  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;

  /**
   * 难度级别
   */
  @TableField(value = "level")
  private Integer level;

  /**
   * 难度名
   */
  @TableField(value = "name")
  private String name;

  /**
   * 最小的空缺格子数
   */
  @TableField(value = "min_empty")
  private Integer minEmpty;

  /**
   * 最大的空缺格子数
   */
  @TableField(value = "max_empty")
  private Integer maxEmpty;
}