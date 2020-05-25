package com.sudoku.model.vo;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户显示层
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserVO implements Serializable {

  private static final long serialVersionUID = -3781968958583675403L;
  /**
   * 用户ID
   */
  private Integer id;

  /**
   * 用户名
   */
  private String username;
  /**
   * 昵称
   */
  private String nickname;
}
