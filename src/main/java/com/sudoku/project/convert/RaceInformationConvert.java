package com.sudoku.project.convert;

import com.sudoku.common.utils.PublicUtils;
import com.sudoku.project.model.bo.RaceInformationBO;
import com.sudoku.project.model.body.RaceInformationBody;
import com.sudoku.project.model.entity.RaceInformation;
import com.sudoku.project.model.vo.RaceInformationVO;
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
   * @return 竞赛信息表对应的实体类对象
   */
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "sudokuMatrix", expression = "java(PublicUtils.compressionIntList(raceInformation.getMatrix()))")
  @Mapping(target = "sudokuHoles", expression = "java(PublicUtils.compressionBoolList(raceInformation.getHoles()))")
  @Mapping(target = "startTime", source = "raceInformation.raceTimeRange.start")
  @Mapping(target = "endTime", source = "raceInformation.raceTimeRange.end")
  RaceInformation convertToVO(RaceInformationBody raceInformation);

  /**
   * 将竞赛信息表对应的实体类对象转换为竞赛信息显示层对象
   *
   * @param raceInformation 竞赛信息表对应的实体类对象
   * @return 竞赛信息显示层对象
   */
  RaceInformationVO convertToVO(RaceInformation raceInformation);

  /**
   * 将竞赛信息表对应的实体类对象转换为竞赛信息业务层对象
   *
   * @param raceInformation 竞赛信息表对应的实体类对象
   * @return 竞赛信息业务层对象
   */
  RaceInformationBO convertToBO(RaceInformation raceInformation);

}
