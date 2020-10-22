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
@Mapper(imports = PageInformationConvert.class)
public interface PageConvert {

  PageConvert INSTANCE = Mappers.getMapper(PageConvert.class);

  @Mapping(target = "pageInformation", expression = "java(PageInformationConvert.INSTANCE.convert(info))")
  @Mapping(target = "list", source = "info.list")
  Page convert(PageInfo info);

  /**
   * 分页信息对象转换器
   */
  @Mapper
  interface PageInformationConvert {

    PageInformationConvert INSTANCE = Mappers.getMapper(PageInformationConvert.class);

    @Mapping(target = "totalPage", source = "pages")
    @Mapping(target = "currentPage", source = "pageNum")
    PageInformation convert(PageInfo info);
  }
}
