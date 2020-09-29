package com.sudoku.project.convert;

import com.sudoku.project.model.bo.RankItemBO;
import com.sudoku.project.model.vo.RankItemVO;
import org.mapstruct.Mapper;

/**
 * 排行项转换器
 */
@Mapper
public interface RankItemConvert {

  /**
   * 将排行项传输层对象转换为显示层对象
   *
   * @param rankItemBO 排行项传输层对象
   * @return 排行项显示层对象
   */
  RankItemVO convert(RankItemBO rankItemBO);
}
