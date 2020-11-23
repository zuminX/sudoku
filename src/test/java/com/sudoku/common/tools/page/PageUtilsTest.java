package com.sudoku.common.tools.page;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyList;
import static org.powermock.api.mockito.PowerMockito.doAnswer;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.spy;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sudoku.common.tools.page.Page.PageInformation;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

/**
 * 分页工具类的参数类
 * <p/>
 * <b color='red'>必须使用JUnit4的方法，PowerMock与JUnit5不兼容</b>
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({PageInfo.class, PageHelper.class})
public class PageUtilsTest {

  /**
   * 待分页数据
   */
  private List<Integer> pageData;
  /**
   * 总页数
   */
  private int totalPage;
  /**
   * 当前页数
   */
  private int currentPage;
  /**
   * 每页条数
   */
  private int pageSize;
  /**
   * 分页信息
   */
  private PageInfo<Integer> pageInfo;

  /**
   * 初始化测试数据
   *
   * @throws Exception when()方法可能抛出的异常
   */
  @Before
  public void setUp() throws Exception {
    pageData = new ArrayList<>();
    IntStream.range(0, 100).forEach(i -> pageData.add(i));
    totalPage = 10;
    currentPage = 2;
    pageSize = 10;

    mockStatic(PageInfo.class);
    spy(PageInfo.class);
    this.pageInfo = spy(buildPageInfo());
    doReturn(pageInfo).when(PageInfo.class, "of", anyList());

    mockStatic(PageHelper.class);
  }

  /**
   * 测试不带类型转换的获取分页数据方法
   */
  @Test
  public void testGetPageWithoutConverter() {
    Page<Integer> page = PageUtils.getPage(buildPageParam());
    assertPageInformation(page.getPageInformation());
    assertPageList(page.getList());
  }

  /**
   * 测试带类型转换的获取分页数据方法
   *
   * @throws Exception when()方法可能抛出的异常
   */
  @Test
  public void testGetPageWithConverter() throws Exception {
    //由于PageInfo会对分页数据进行处理，故需要Mock掉该逻辑，让其返回分页且进行类型转换的分页数据
    doAnswer(answer -> {
      List<String> argumentList = answer.getArgument(0);
      List<String> pageList = argumentList.subList((currentPage - 1) * pageSize, currentPage * pageSize);
      Whitebox.setInternalState(pageInfo, "list", pageList);
      return pageList;
    }).when(pageInfo, "setList", anyList());

    Function<Integer, String> converter = String::valueOf;
    Page<String> page = PageUtils.getPage(buildPageParam(), converter);
    assertPageInformation(page.getPageInformation());
    assertPageList(page.getList(), converter);
  }

  /**
   * 验证分页数据列表
   *
   * @param list 数据列表
   */
  private void assertPageList(List<Integer> list) {
    assertPageList(list, Function.identity());
  }

  /**
   * 验证分页数据列表
   *
   * @param list      数据列表
   * @param converter 类型转换器
   * @param <T>       目标类型
   */
  private <T> void assertPageList(List<T> list, Function<Integer, T> converter) {
    assertNotNull(list);
    assertEquals(pageSize, list.size());
    for (int i = (currentPage - 1) * pageSize, index = 0; i < currentPage * pageSize; i++, index++) {
      assertEquals(converter.apply(pageData.get(i)), list.get(index));
    }
  }

  /**
   * 验证分页信息
   *
   * @param pageInformation 分页信息对象
   */
  private void assertPageInformation(PageInformation pageInformation) {
    assertNotNull(pageInformation);
    assertEquals(totalPage, pageInformation.getTotalPage().intValue());
    assertEquals(currentPage, pageInformation.getCurrentPage().intValue());
    assertEquals(pageSize, pageInformation.getPageSize().intValue());
  }

  /**
   * 构建分页参数
   *
   * @return 分页参数
   */
  private PageParam<Integer> buildPageParam() {
    Supplier<List<Integer>> queryFunc = () -> pageData;
    return PageParam.<Integer>builder().page(currentPage).pageSize(pageSize).queryFunc(queryFunc).build();
  }

  /**
   * 构建分页信息
   *
   * @return 分页信息对象
   */
  private PageInfo<Integer> buildPageInfo() {
    PageInfo<Integer> pageInfo = new PageInfo<>();
    pageInfo.setPageSize(pageSize);
    pageInfo.setPageNum(currentPage);
    pageInfo.setPages(totalPage);
    List<Integer> list = IntStream.range((currentPage - 1) * pageSize, currentPage * pageSize)
        .mapToObj(i -> pageData.get(i))
        .collect(Collectors.toList());
    pageInfo.setList(list);
    return pageInfo;
  }
}