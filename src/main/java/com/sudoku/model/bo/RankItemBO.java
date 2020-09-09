package com.sudoku.model.bo;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 排行项业务类
 *
 * @param <T> 数据的类型
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RankItemBO<T> implements Serializable {

  private static final long serialVersionUID = 1559554219049093889L;

  /**
   * 数独等级名称
   */
  private String sudokuLevelName;

  /**
   * 用户昵称
   */
  private String nickname;

  /**
   * 排行项数据
   */
  private T data;
}
