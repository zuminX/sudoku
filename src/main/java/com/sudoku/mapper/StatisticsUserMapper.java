package com.sudoku.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sudoku.model.bo.StatisticsUserDataBO;
import com.sudoku.model.entity.StatisticsUser;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface StatisticsUserMapper extends BaseMapper<StatisticsUser> {

  /**
   * 查询最新统计的日期
   *
   * @param dateName 统计的日期名
   * @return 最新日期
   */
  LocalDateTime findFirstDateByDateNameOrderByDateDesc(@Param("dateName") String dateName);

  /**
   * 查询在[startDate,endDate)中，统计日期名字为dateName的用户的统计数据列表
   *
   * @param startDate 开始时间
   * @param endDate   结束时间
   * @param dateName  统计日期名字
   * @return 用户的统计数据列表
   */
  List<StatisticsUserDataBO> selectUserTotalAndUserActiveTotalByDateBetweenAndDateName(@Param("startDate") LocalDateTime startDate,
      @Param("endDate") LocalDateTime endDate, @Param("dateName") String dateName);


}