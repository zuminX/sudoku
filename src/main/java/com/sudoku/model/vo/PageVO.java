package com.sudoku.model.vo;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分页数据显示层类
 *
 * @param <T> 数据类型
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PageVO<T> implements Serializable {

  private static final long serialVersionUID = 4427124527401721636L;

  /**
   * 分页信息
   */
  private PageInformation pageInformation;

  /**
   * 分页数据
   */
  private List<T> list;

  @NoArgsConstructor
  @AllArgsConstructor
  @Data
  public static class PageInformation implements Serializable {

    private static final long serialVersionUID = 8500765176094961217L;

    /**
     * 总页数
     */
    private Integer totalPage;

    /**
     * 当前页
     */
    private Integer currentPage;

    /**
     * 每页的数量
     */
    private Integer pageSize;
  }
}
