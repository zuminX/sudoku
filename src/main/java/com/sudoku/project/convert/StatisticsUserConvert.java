package com.sudoku.project.convert;

import com.sudoku.project.model.bo.StatisticsUserDataBO;
import com.sudoku.project.model.entity.StatisticsUser;
import java.time.LocalDate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * 统计用户类的转换器
 */
@Mapper
public interface StatisticsUserConvert {

  StatisticsUserConvert INSTANCE = Mappers.getMapper(StatisticsUserConvert.class);

  /**
   * 将用户统计数据类转化为统计用户表对应的实体类
   *
   * @param dataBO   用户统计数据类对象
   * @param dateName 日期名
   * @param date     日期
   * @return 统计用户表对应的实体类对象
   */
  @Mappings({
      @Mapping(target = "newUserTotal", source = "dataBO.newUserTotal"),
      @Mapping(target = "activeUserTotal", source = "dataBO.activeUserTotal"),
      @Mapping(target = "id", ignore = true)
  })
  StatisticsUser convert(StatisticsUserDataBO dataBO, String dateName, LocalDate date);
}
