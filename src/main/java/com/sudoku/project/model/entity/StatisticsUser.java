package com.sudoku.project.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@TableName(value = "statistics_user")
public class StatisticsUser implements Serializable {

  private static final long serialVersionUID = -3821008426308955938L;
  /**
   * 用户统计数据的ID
   */
  @TableId(value = "id", type = IdType.INPUT)
  private Integer id;

  /**
   * 用户总数
   */
  @TableField(value = "user_total")
  private Integer userTotal;

  /**
   * 用户活跃总数
   */
  @TableField(value = "user_active_total")
  private Integer userActiveTotal;

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
}