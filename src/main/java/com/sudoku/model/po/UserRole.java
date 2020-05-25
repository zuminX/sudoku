package com.sudoku.model.po;

import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户角色表对应的实体类
 */
@NoArgsConstructor
@Data
public class UserRole implements Serializable {

  private static final long serialVersionUID = -4308666025286726872L;
  /**
   * 用户角色ID
   */
  private Integer id;

  /**
   * 用户ID
   */
  private Integer uid;

  /**
   * 角色ID
   */
  private Integer rid;

  public UserRole(Integer uid, Integer rid) {
    this.uid = uid;
    this.rid = rid;
  }
}