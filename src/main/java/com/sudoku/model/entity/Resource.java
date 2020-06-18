package com.sudoku.model.entity;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * 资源表对应的实体类
 */
@Data
public class Resource implements Serializable {

  private static final long serialVersionUID = 9114724268170512173L;
  /**
   * 资源ID
   */
  private Integer id;

  /**
   * 资源路径
   */
  private String url;

  /**
   * 资源名称
   */
  private String nameZh;

  /**
   * 角色列表
   */
  private List<Role> roles;

}