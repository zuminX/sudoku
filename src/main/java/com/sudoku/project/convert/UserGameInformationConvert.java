package com.sudoku.project.convert;

import com.sudoku.project.model.entity.UserGameInformation;
import com.sudoku.project.model.vo.UserGameInformationVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * 用户游戏信息转换器
 */
@Mapper
public interface UserGameInformationConvert {

  /**
   * 将用户游戏信息表对应的对象转换为显示层对象
   *
   * @param userGameInformation 用户游戏信息表对应的对象
   * @return 用户游戏信息显示层对象
   */
  @Mapping(target = "sudokuLevelName", ignore = true)
  UserGameInformationVO convert(UserGameInformation userGameInformation);

}
