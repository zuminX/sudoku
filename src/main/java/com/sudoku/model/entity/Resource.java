package com.sudoku.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * 资源表对应的实体类
 */
@Data
@TableName(value = "`resource`")
public class Resource implements Serializable {

  private static final long serialVersionUID = 8877877758938672841L;
  /**
   * 资源ID
   */
  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;

  /**
   * 资源路径
   */
  @TableField(value = "url")
  private String url;

  /**
   * 资源名称
   */
  @TableField(value = "nameZh")
  private String nameZh;

  /**
   * 角色列表
   */
  private List<Role> roles;
}