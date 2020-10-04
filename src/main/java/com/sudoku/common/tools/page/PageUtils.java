package com.sudoku.common.tools.page;

import static java.util.stream.Collectors.toList;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.util.List;
import java.util.function.Function;
import org.springframework.stereotype.Component;

/**
 * 分页工具类
 */
@Component
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
  public static <T> Page<T> getPage(PageParam<T> pageParam) {
    return new PageTemplateMethod<T>(pageParam) {
      @Override
      public PageInfo<T> getPageInfo(List<T> queryList) {
        return new PageInfo<>(queryList);
      }
    }.getPage();
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
  @SuppressWarnings("all")
  public static <T, V> Page<V> getPage(PageParam<T> pageParam, Function<T, V> converter) {
    return (Page<V>) new PageTemplateMethod<T>(pageParam) {
      /**
       * 将转换后的数据代替原先分页查询的数据
       * @param queryList 查询数据列表
       * @return 分页信息对象
       */
      @Override
      public PageInfo<T> getPageInfo(List<T> queryList) {
        List<V> targetList = queryList.stream().map(converter).collect(toList());
        PageInfo pageInfo = new PageInfo(queryList);
        pageInfo.setList(targetList);
        return pageInfo;
      }
    }.getPage();
  }

  /**
   * 获取分页数据的模板方法
   *
   * @param <T> 分页数据的类型
   */
  private static abstract class PageTemplateMethod<T> {

    private final PageParam<T> pageParam;

    public PageTemplateMethod(PageParam<T> pageParam) {
      this.pageParam = pageParam;
    }

    /**
     * 获取分页信息对象
     *
     * @param queryList 查询数据列表
     * @return 分页信息对象
     */
    public abstract PageInfo<T> getPageInfo(List<T> queryList);

    /**
     * 获取分页数据
     *
     * @return 分页数据
     */
    public Page<T> getPage() {
      setStartPage(pageParam.getPage(), pageParam.getPageSize());
      List<T> queryList = pageParam.getQueryFunc().get();
      return PageConvert.INSTANCE.convert(getPageInfo(queryList));
    }

    /**
     * 设置开始分页
     *
     * @param page     查询的页数
     * @param pageSize 每页个数
     */
    private void setStartPage(Integer page, Integer pageSize) {
      PageHelper.startPage(page == null ? DEFAULT_PAGE : page, pageSize == null ? DEFAULT_PAGE_SIZE : pageSize);
    }
  }
}
