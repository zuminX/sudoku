package com.sudoku.project.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import lombok.Data;

/**
 * 用户-角色中间表对应的实体类
 */
@Data
@TableName(value = "merge_user_role")
public class MergeUserRole implements Serializable {

  private static final long serialVersionUID = 6802935173945726932L;

  /**
   * 用户角色ID
   */
  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;

  /**
   * 用户ID
   */
  @TableField(value = "user_id")
  private Integer userId;

  /**
   * 角色ID
   */
  @TableField(value = "role_id")
  private Integer roleId;

  public MergeUserRole(Integer userId, Integer roleId) {
    this.userId = userId;
    this.roleId = roleId;
  }
}