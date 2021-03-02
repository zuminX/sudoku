package com.sudoku.common.tools.page;

import static java.util.stream.Collectors.toList;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sudoku.common.constant.enums.StatusCode;
import com.sudoku.common.exception.PageException;
import com.sudoku.common.tools.ServletUtils;
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
   * 最大分页条数
   */
  private static final int MAX_PAGE_SIZE = 20;
  /**
   * 每页显示条数的参数名称
   */
  private static final String PAGE_SIZE_NAME = "pageSize";
  /**
   * 默认查询的页数
   */
  private static final Integer DEFAULT_PAGE = 1;
  /**
   * 默认每页个数
   */
  private static final Integer DEFAULT_PAGE_SIZE = 5;
  /**
   * 当前查询页的参数名称
   */
  private static final String PAGE_NAME = "page";

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
      PageHelper.startPage(getPage(), getPageSize());
      List<T> queryList = pageParam.getQueryFunc().get();
      return PageConvert.INSTANCE.convert(callBack.getPageInfo(queryList));
    }

    /**
     * 获取查询页数
     *
     * @return 查询页数
     */
    private int getPage() {
      Integer page = pageParam.getPage();
      return page != null ? page : ServletUtils.getParameterToInt(PAGE_NAME, DEFAULT_PAGE);
    }

    /**
     * 获取每页个数
     *
     * @return 每页个数
     */
    private int getPageSize() {
      Integer pageSize = pageParam.getPageSize();
      if (pageSize == null) {
        pageSize = ServletUtils.getParameterToInt(PAGE_SIZE_NAME, DEFAULT_PAGE_SIZE);
      }
      if (pageSize <= 0 || pageSize > MAX_PAGE_SIZE) {
        throw new PageException(StatusCode.PAGE_SIZE_ILLEGAL);
      }
      return pageSize;
    }
  }
}
