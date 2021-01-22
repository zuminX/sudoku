package com.sudoku.project.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 普通游戏记录表对应的实体类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "normal_game_record")
public class NormalGameRecord implements Serializable {

  private static final long serialVersionUID = -2384380261552269284L;

  /**
   * 游戏记录的ID
   */
  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;

  /**
   * 输入的数独矩阵
   */
  @TableField(value = "input_matrix")
  private String inputMatrix;

  /**
   * 回答情况
   */
  @TableField(value = "answer_situation")
  private Integer answerSituation;

  /**
   * 用户ID
   */
  @TableField(value = "user_id")
  private Integer userId;

  /**
   * 数独记录ID
   */
  @TableField(value = "sudoku_record_id")
  private Integer sudokuRecordId;
}