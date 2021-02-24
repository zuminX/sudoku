package com.sudoku.game.convert;

import com.sudoku.common.utils.PublicUtils;
import com.sudoku.game.model.body.RaceInformationBody;
import com.sudoku.game.model.entity.RaceInformation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * 竞赛信息对象转换器
 */
@Mapper(imports = PublicUtils.class)
public interface RaceInformationConvert {

  /**
   * 将竞赛内容信息对象转换为竞赛信息表对应的实体类对象
   *
   * @param raceInformation 竞赛内容信息对象
   * @param sudokuRecordId  数独记录ID
   * @param creatorUserId   创建者ID
   * @return 竞赛信息表对应的实体类对象
   */
  @Mapping(target = "id", ignore = true)
  RaceInformation convert(RaceInformationBody raceInformation, Integer sudokuRecordId, Integer creatorUserId);
}
