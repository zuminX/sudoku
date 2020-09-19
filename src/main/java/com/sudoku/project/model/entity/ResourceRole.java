package com.sudoku.project.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 资源角色对应的实体类
 */
@Data
@TableName(value = "resource_role")
public class ResourceRole implements Serializable {

  private static final long serialVersionUID = -6996653235064535889L;

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