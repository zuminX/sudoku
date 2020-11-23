package com.sudoku.project.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import lombok.Data;

@Data
@TableName(value = "merge_resource_role")
public class MergeResourceRole implements Serializable {


  private static final long serialVersionUID = -1138514723502720806L;

  /**
   * 资源角色ID
   */
  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;

  /**
   * 资源ID
   */
  @TableField(value = "resource_id")
  private Integer resourceId;

  /**
   * 角色ID
   */
  @TableField(value = "role_id")
  private Integer roleId;
}