package com.sudoku.common.tools.page;

import com.github.pagehelper.PageInfo;
import com.sudoku.common.tools.page.Page.PageInformation;
import com.sudoku.common.tools.page.PageConvert.PageInformationConvert;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * 分页对象转换器
 */
@Mapper(uses = PageInformationConvert.class)
@SuppressWarnings("rawtypes")
public interface PageConvert {

  /**
   * 分页对象转换器的实例
   */
  PageConvert INSTANCE = Mappers.getMapper(PageConvert.class);

  /**
   * 将分页详情对象转换为分页数据对象
   *
   * @param info 分页详情对象
   * @return 分页数据对象
   */
  @Mapping(target = "pageInformation", source = "info")
  @Mapping(target = "list", source = "info.list")
  Page convert(PageInfo info);

  /**
   * 分页信息对象转换器
   */
  @Mapper
  interface PageInformationConvert {

    /**
     * 将分页详情对象转换为分页信息对象
     *
     * @param info 分页详情对象
     * @return 分页信息对象
     */
    @Mapping(target = "totalPage", source = "pages")
    @Mapping(target = "currentPage", source = "pageNum")
    PageInformation convert(PageInfo info);
  }
}
