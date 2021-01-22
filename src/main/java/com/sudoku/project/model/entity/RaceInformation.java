package com.sudoku.project.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 竞赛信息表对应的实体类
 */
@Data
@TableName(value = "race_information")
public class RaceInformation implements Serializable {

  private static final long serialVersionUID = 321607305176124761L;

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
   * 创建用户的ID
   */
  @TableField(value = "creator_user_id")
  private Integer creatorUserId;

  /**
   * 数独记录ID
   */
  @TableField(value = "sudoku_record_id")
  private Integer sudokuRecordId;
}