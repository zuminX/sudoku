package com.sudoku.project.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@TableName(value = "race_information")
public class RaceInformation implements Serializable {


  private static final long serialVersionUID = 8663649210686937610L;

  /**
   * 竞赛信息的ID
   */
  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;

  /**
   * 竞赛的标题
   */
  @TableField(value = "title")
  private String title;

  /**
   * 竞赛的描述
   */
  @TableField(value = "description")
  private String description;

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
}