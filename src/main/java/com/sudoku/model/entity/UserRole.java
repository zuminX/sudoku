package com.sudoku.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 用户角色表对应的实体类
 */
@Data
@TableName(value = "user_role")
public class UserRole implements Serializable {

  private static final long serialVersionUID = -5198486341431696116L;
  /**
   * 用户角色ID
   */
  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;

  /**
   * 用户ID
   */
  @TableField(value = "uid")
  private Integer uid;

  /**
   * 角色ID
   */
  @TableField(value = "rid")
  private Integer rid;

  public UserRole(Integer uid, Integer rid) {
    this.uid = uid;
    this.rid = rid;
  }

}