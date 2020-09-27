package com.sudoku.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sudoku.project.model.bo.StatisticsUserDataBO;
import com.sudoku.project.model.entity.StatisticsUser;
import java.time.LocalDate;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface StatisticsUserMapper extends BaseMapper<StatisticsUser> {

  /**
   * 查询最新统计的日期
   *
   * @param dateName 统计的日期名
   * @return 最新日期
   */
  LocalDate findFirstDateByDateNameOrderByDateDesc(@Param("dateName") String dateName);

  /**
   * 查询在[startDate,endDate)中，统计日期名字为dateName的用户的统计数据列表
   *
   * @param startDate 开始时间
   * @param endDate   结束时间
   * @param dateName  统计日期名字
   * @return 用户的统计数据列表
   */
  List<StatisticsUserDataBO> selectNewUserTotalAndActiveUserTotalByDateBetweenAndDateName(@Param("startDate") LocalDate startDate,
      @Param("endDate") LocalDate endDate, @Param("dateName") String dateName);

  /**
   * 查询在[startDate,endDate)中，统计日期名字为dateName的用户的统计数据的总和
   *
   * @param startDate 开始时间
   * @param endDate   结束时间
   * @param dateName  统计日期名字
   * @return 用户的统计数据的总和
   */
  StatisticsUserDataBO selectNewUserTotalSumAndActiveUserTotalSumByDateBetweenAndDateName(@Param("startDate") LocalDate startDate,
      @Param("endDate") LocalDate endDate, @Param("dateName") String dateName);

  /**
   * 查询统计日期名字为dateName的用户总数
   *
   * @return 用户总数
   */
  Integer selectNewUserTotalSumByDateName(@Param("dateName") String dateName);

  /**
   * 查询在startDate后，统计日期名字为dateName的用户总数
   *
   * @return 用户总数
   */
  Integer selectNewUserTotalSumByDateAfterAndDateName(@Param("startDate") LocalDate startDate, @Param("dateName") String dateName);
}