package com.sudoku.model.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * 资源角色对应的实体类
 */
@Data
public class ResourceRole implements Serializable {

  private static final long serialVersionUID = -3156642026650822947L;
  /**
   * 资源角色ID
   */
  private Integer id;

  /**
   * 资源ID
   */
  private Integer rrid;

  /**
   * 角色ID
   */
  private Integer rid;
}