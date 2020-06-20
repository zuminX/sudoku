package com.sudoku.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 角色表对应的实体类
 */
@Data
@TableName(value = "`role`")
public class Role implements Serializable {

  private static final long serialVersionUID = -5419147180433797930L;
  /**
   * 角色ID
   */
  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;

  /**
   * 角色名
   */
  @TableField(value = "name")
  private String name;

  /**
   * 角色名称
   */
  @TableField(value = "nameZh")
  private String nameZh;
}