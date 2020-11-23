package com.sudoku.project.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 游戏记录表对应的实体类
 */
@Data
@TableName(value = "game_record")
public class GameRecord implements Serializable {


  private static final long serialVersionUID = 9118930828203696159L;

  /**
   * 游戏记录的ID
   */
  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;

  /**
   * 数独矩阵
   */
  @TableField(value = "sudoku_matrix")
  private String sudokuMatrix;

  /**
   * 空缺的数独
   */
  @TableField(value = "sudoku_holes")
  private String sudokuHoles;

  /**
   * 开始时间
   */
  @TableField(value = "start_time")
  private LocalDateTime startTime;

  /**
   * 结束时间
   */
  @TableField(value = "end_time")
  private LocalDateTime endTime;

  /**
   * 回答是否正确
   */
  @TableField(value = "correct")
  private Boolean correct;

  /**
   * 数独难度ID
   */
  @TableField(value = "sudoku_level_id")
  private Integer sudokuLevelId;

  /**
   * 用户ID
   */
  @TableField(value = "user_id")
  private Integer userId;
}