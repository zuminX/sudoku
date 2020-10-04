package com.sudoku.common.tools.page;


import java.util.List;
import java.util.function.Supplier;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 分页参数
 *
 * @param <T> 源数据类型
 */
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PageParam<T> {

  /**
   * 分页查询方法
   */
  private Supplier<List<T>> queryFunc;
  /**
   * 查询的页数
   */
  private Integer page;
  /**
   * 每页个数
   */
  private Integer pageSize;
}
