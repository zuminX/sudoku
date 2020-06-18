package com.sudoku.model.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * 角色表对应的实体类
 */
@Data
public class Role implements Serializable {

  private static final long serialVersionUID = 112626075075595122L;
  /**
   * 角色ID
   */
  private Integer id;

  /**
   * 角色名
   */
  private String name;

  /**
   * 角色名称
   */
  private String nameZh;
}