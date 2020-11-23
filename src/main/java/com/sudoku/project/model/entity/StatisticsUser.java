package com.sudoku.project.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDate;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统计用户表对应的实体类
 */
@Data
@NoArgsConstructor
@TableName(value = "statistics_user")
public class StatisticsUser implements Serializable {


  private static final long serialVersionUID = -6871484199539215302L;

  /**
   * 用户统计数据的ID
   */
  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;

  /**
   * 新增用户总数
   */
  @TableField(value = "new_user_total")
  private Integer newUserTotal;

  /**
   * 活跃用户总数
   */
  @TableField(value = "active_user_total")
  private Integer activeUserTotal;

  /**
   * 统计日期类型的名字
   */
  @TableField(value = "date_name")
  private String dateName;

  /**
   * 统计的日期
   */
  @TableField(value = "`date`")
  private LocalDate date;
}