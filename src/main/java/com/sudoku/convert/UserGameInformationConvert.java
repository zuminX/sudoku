package com.sudoku.convert;

import com.sudoku.model.po.UserGameInformation;
import com.sudoku.model.vo.UserGameInformationVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * 用户游戏信息转换器
 */
@Mapper
public interface UserGameInformationConvert {

  UserGameInformationConvert INSTANCE = Mappers.getMapper(UserGameInformationConvert.class);

  /**
   * 将用户游戏信息表对应的对象转换为显示层对象
   *
   * @param userGameInformation 用户游戏信息表对应的对象
   * @return 用户游戏信息显示层对象
   */
  @Mapping(target = "sudokuLevelName", ignore = true)
  UserGameInformationVO convert(UserGameInformation userGameInformation);

}
