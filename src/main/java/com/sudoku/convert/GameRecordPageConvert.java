package com.sudoku.convert;

import com.github.pagehelper.PageInfo;
import com.sudoku.model.vo.GameRecordPageVO;
import com.sudoku.model.vo.GameRecordVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * 游戏记录分页数据转换器
 */
@Mapper
public interface GameRecordPageConvert {

  GameRecordPageConvert INSTANCE = Mappers.getMapper(GameRecordPageConvert.class);

  /**
   * 将游戏记录显示层的分页对象转换为游戏记录分页数据显示层
   *
   * @param gameRecordPageInfo 游戏记录显示层的分页对象
   * @return 游戏记录分页数据显示层对象
   */
  @Mapping(target = "gameRecord", source = "gameRecordPageInfo.list")
  @Mapping(target = "totalPage", source = "pages")
  @Mapping(target = "currentPage", source = "pageNum")
  GameRecordPageVO convert(PageInfo<GameRecordVO> gameRecordPageInfo);
}
