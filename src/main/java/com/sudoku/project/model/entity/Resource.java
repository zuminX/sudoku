package com.sudoku.project.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import lombok.Data;

/**
 * 资源表对应的实体类
 */
@Data
@TableName(value = "`resource`")
public class Resource implements Serializable {

  private static final long serialVersionUID = -7035970422635900016L;

  /**
   * 资源ID
   */
  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;

  /**
   * 权限标识
   */
  @TableField(value = "perms")
  private String perms;

  /**
   * 资源名称
   */
  @TableField(value = "name_zh")
  private String nameZh;

}