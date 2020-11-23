package com.sudoku.project.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@TableName(value = "race_record")
public class RaceRecord implements Serializable {


  private static final long serialVersionUID = 9100395626494592143L;

  /**
   * 比赛记录的ID
   */
  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;
  /**
   * 输入的数独矩阵
   */
  @TableField(value = "input_matrix")
  private String inputMatrix;
  /**
   * 回答是否正确
   */
  @TableField(value = "correct")
  private Byte correct;
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
   * 用户ID
   */
  @TableField(value = "user_id")
  private Integer userId;
  /**
   * 比赛信息的ID
   */
  @TableField(value = "race_information_id")
  private Integer raceInformationId;
}