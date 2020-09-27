package com.sudoku.project.convert;

import com.sudoku.project.model.bo.StatisticsGameDataBO;
import com.sudoku.project.model.entity.StatisticsGame;
import java.time.LocalDate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * 统计游戏类的转换器
 */
@Mapper
public interface StatisticsGameConvert {

  StatisticsGameConvert INSTANCE = Mappers.getMapper(StatisticsGameConvert.class);

  /**
   * 将游戏统计数据类转化为统计游戏表对应的实体类
   *
   * @param dataBO   游戏统计数据类对象
   * @param dateName 日期名
   * @param date     日期
   * @return 统计游戏表对应的实体类对象
   */
  @Mappings({
      @Mapping(target = "correctTotal", source = "dataBO.correctTotal"),
      @Mapping(target = "errorTotal", source = "dataBO.errorTotal"),
      @Mapping(target = "sudokuLevelId", source = "dataBO.sudokuLevelId"),
      @Mapping(target = "id", ignore = true),
  })
  StatisticsGame convert(StatisticsGameDataBO dataBO, String dateName, LocalDate date);
}
