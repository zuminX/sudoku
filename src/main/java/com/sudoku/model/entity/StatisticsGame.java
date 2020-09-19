package com.sudoku.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@TableName(value = "statistics_game")
public class StatisticsGame implements Serializable {

  /**
   * 游戏统计数据的ID
   */
  @TableId(value = "id", type = IdType.INPUT)
  private Integer id;

  /**
   * 提交正确的总数
   */
  @TableField(value = "correct_total")
  private Integer correctTotal;

  /**
   * 提交错误的总数
   */
  @TableField(value = "error_total")
  private Integer errorTotal;

  /**
   * 数独等级ID
   */
  @TableField(value = "sudoku_level_id")
  private Integer sudokuLevelId;

  /**
   * 统计日期类型的名字
   */
  @TableField(value = "date_name")
  private String dateName;

  /**
   * 统计的日期
   */
  @TableField(value = "`date`")
  private LocalDateTime date;

  private static final long serialVersionUID = 1L;
}