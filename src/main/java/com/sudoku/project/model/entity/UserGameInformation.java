package com.sudoku.project.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import lombok.Data;

/**
 * 用户游戏信息表对应的实体类
 */
@Data
@TableName(value = "user_game_information")
public class UserGameInformation implements Serializable {


  private static final long serialVersionUID = 4670246285232369453L;

  /**
   * 用户游戏信息的ID
   */
  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;

  /**
   * 提交的次数
   */
  @TableField(value = "total")
  private Integer total;

  /**
   * 提交正确的次数
   */
  @TableField(value = "correct_number")
  private Integer correctNumber;

  /**
   * 平均用时
   */
  @TableField(value = "average_spend_time")
  private Integer averageSpendTime;

  /**
   * 最短用时
   */
  @TableField(value = "min_spend_time")
  private Integer minSpendTime;

  /**
   * 最长用时
   */
  @TableField(value = "max_spend_time")
  private Integer maxSpendTime;

  /**
   * 用户ID
   */
  @TableField(value = "user_id")
  private Integer userId;

  /**
   * 数独等级ID
   */
  @TableField(value = "sudoku_level_id")
  private Integer sudokuLevelId;
}