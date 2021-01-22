package com.sudoku.project.model.bo;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 排行项数据业务类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RankItemDataBO implements Serializable {

  private static final long serialVersionUID = -1532258014558412367L;

  /**
   * 用户名
   */
  private String username;

  /**
   * 排行项数据
   */
  private Integer data;
}
