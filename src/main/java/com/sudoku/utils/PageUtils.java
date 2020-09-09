package com.sudoku.utils;

import static java.util.stream.Collectors.toList;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sudoku.convert.PageConvert;
import com.sudoku.model.vo.PageVO;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

/**
 * 分页工具类
 */
public class PageUtils {

  /**
   * 默认查询的页数
   */
  private static final Integer DEFAULT_PAGE = 1;

  /**
   * 默认每页个数
   */
  private static final Integer DEFAULT_PAGE_SIZE = 5;

  /**
   * 私有构造方法，防止实例化
   */
  private PageUtils() {
  }

  /**
   * 获取分页数据
   *
   * @param pageParam 分页参数
   * @param <T>       源数据类型
   * @return 分页数据
   */
  public static <T> PageVO<T> getPageVO(PageParam<T> pageParam) {
    setStartPage(pageParam.page, pageParam.pageSize);
    List<T> queryList = pageParam.queryFunc.get();
    PageInfo<T> pageInfo = new PageInfo<>(queryList);
    return PageConvert.INSTANCE.convert(pageInfo);
  }

  /**
   * 获取分页数据，并利用转换器转化为指定类型
   *
   * @param pageParam 分页参数
   * @param converter 源数据转为目标数据的转换器
   * @param <T>       源数据类型
   * @param <V>       目标数据类型
   * @return 分页数据
   */
  public static <T, V> PageVO<V> getPageVO(PageParam<T> pageParam, Function<T, V> converter) {
    setStartPage(pageParam.page, pageParam.pageSize);
    List<T> queryList = pageParam.queryFunc.get();
    PageInfo<V> pageInfo = getConvert(queryList, converter);
    return PageConvert.INSTANCE.convert(pageInfo);
  }

  /**
   * 设置开始分页
   *
   * @param page     查询的页数
   * @param pageSize 每页个数
   */
  private static void setStartPage(Integer page, Integer pageSize) {
    PageHelper.startPage(page == null ? DEFAULT_PAGE : page, pageSize == null ? DEFAULT_PAGE_SIZE : pageSize);
  }

  /**
   * 将转换后的数据代替原先分页查询的数据
   *
   * @param sourceList 原始分页数据列表
   * @param converter  转换器
   * @param <T>        源数据类型
   * @param <V>        目标数据类型
   * @return 分页信息
   */
  @SuppressWarnings("all")
  private static <T, V> PageInfo<V> getConvert(List<T> sourceList, Function<T, V> converter) {
    List<V> targetList = sourceList.stream().map(converter).collect(toList());
    PageInfo pageInfo = new PageInfo(sourceList);
    pageInfo.setList(targetList);
    return pageInfo;
  }

  /**
   * 分页参数
   *
   * @param <T> 源数据类型
   */
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class PageParam<T> {

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
}
