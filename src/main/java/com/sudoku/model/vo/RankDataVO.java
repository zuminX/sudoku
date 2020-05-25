package com.sudoku.model.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 排行数据显示层
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RankDataVO<T> implements Serializable {

  private static final long serialVersionUID = 5967517292862185830L;
  /**
   * 排行的名称
   */
  private String rankName;
  /**
   * 排行项映射 key为难度名，value为排行项集合
   */
  private Map<String, List<RankItemVO<T>>> rankItemMap;

  /**
   * 排行项显示层
   *
   * @param <T> 数据的类型
   */
  @NoArgsConstructor
  @AllArgsConstructor
  @Data
  public static class RankItemVO<T> implements Serializable {

    private static final long serialVersionUID = 5068815021945647289L;
    /**
     * 用户昵称
     */
    private String nickname;
    /**
     * 排行项数据
     */
    private T data;
  }

}
