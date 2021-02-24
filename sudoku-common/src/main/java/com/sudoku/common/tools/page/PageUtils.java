package com.sudoku.common.tools.page;

import static java.util.stream.Collectors.toList;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.util.List;
import java.util.function.Function;
import org.jetbrains.annotations.NotNull;
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
  public static <T> Page<T> getPage(@NotNull PageParam<T> pageParam) {
    PageTemplateMethod<T> pageTemplateMethod = new PageTemplateMethod<>(pageParam);
    return pageTemplateMethod.getPage(PageInfo::of);
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
  public static <T, V> Page<V> getPage(@NotNull PageParam<T> pageParam, @NotNull Function<T, V> converter) {
    PageTemplateMethod<T> pageTemplateMethod = new PageTemplateMethod<>(pageParam);
    return (Page<V>) pageTemplateMethod.getPage(queryList -> {
      List<V> targetList = queryList.stream().map(converter).collect(toList());
      PageInfo pageInfo = PageInfo.of(queryList);
      pageInfo.setList(targetList);
      return pageInfo;
    });
  }

  /**
   * 获取分页详情对象的回调方法
   *
   * @param <T> 分页数据的类型
   */
  private interface GetPageInfoCallBack<T> {

    /**
     * 获取分页详情对象
     *
     * @param queryList 查询数据列表
     * @return 分页详情对象
     */
    PageInfo<T> getPageInfo(List<T> queryList);
  }

  /**
   * 获取分页数据的模板方法
   *
   * @param <T> 分页数据的类型
   */
  private static class PageTemplateMethod<T> {

    private final PageParam<T> pageParam;

    public PageTemplateMethod(PageParam<T> pageParam) {
      this.pageParam = pageParam;
    }

    /**
     * 获取分页数据
     *
     * @param callBack 获取分页详情对象的回调方法
     * @return 分页数据
     */
    @SuppressWarnings("all")
    public Page<T> getPage(GetPageInfoCallBack<T> callBack) {
      setStartPage(pageParam.getPage(), pageParam.getPageSize());
      List<T> queryList = pageParam.getQueryFunc().get();
      return PageConvert.INSTANCE.convert(callBack.getPageInfo(queryList));
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
