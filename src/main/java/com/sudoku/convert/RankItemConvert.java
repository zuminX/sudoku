package com.sudoku.convert;

import com.sudoku.model.dto.RankItemDTO;
import com.sudoku.model.vo.RankDataVO.RankItemVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 排行项转换器
 */
@Mapper
public interface RankItemConvert {

  RankItemConvert INSTANCE = Mappers.getMapper(RankItemConvert.class);

  /**
   * 将排行项传输层对象转换为显示层对象
   *
   * @param rankItemDTO 排行项传输层对象
   * @return 排行项显示层对象
   */
  RankItemVO convert(RankItemDTO rankItemDTO);
}
