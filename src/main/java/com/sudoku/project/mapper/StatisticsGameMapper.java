package com.sudoku.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sudoku.project.model.bo.StatisticsGameDataBO;
import com.sudoku.project.model.entity.StatisticsGame;
import java.time.LocalDate;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface StatisticsGameMapper extends BaseMapper<StatisticsGame> {

  /**
   * 查询最新统计的日期
   *
   * @param dateName 统计的日期名
   * @return 最新日期
   */
  LocalDate findFirstDateByDateNameOrderByDateDesc(@Param("dateName") String dateName);

  /**
   * 查询统计日期名字为dateName的游戏总局数
   *
   * @return 游戏总局数
   */
  Integer selectGameTotalByDateName(@Param("dateName") String dateName);

  /**
   * 查询在[startDate,endDate)中，统计日期名字为dateName的游戏总局数
   *
   * @param startDate 开始时间
   * @param endDate   结束时间
   * @param dateName  统计日期名字
   * @return 游戏总局数
   */
  List<Integer> selectGameTotalByDateBetweenAndDateName(@Param("startDate") LocalDate startDate,
      @Param("endDate") LocalDate endDate, @Param("dateName") String dateName);

  /**
   * 查询在[startDate,endDate)中，统计日期名字为dateName的游戏统计数据列表
   *
   * @param startDate 开始时间
   * @param endDate   结束时间
   * @param dateName  统计日期名字
   * @return 游戏统计数据列表
   */
  List<StatisticsGameDataBO> selectCorrectTotalAndErrorTotalAndSudokuLevelIdByDateBetweenAndDateName(
      @Param("startDate") LocalDate startDate,
      @Param("endDate") LocalDate endDate, @Param("dateName") String dateName);

  /**
   * 查询在[startDate,endDate)中，统计日期名字为dateName的游戏统计数据总和的列表
   *
   * @param startDate 开始时间
   * @param endDate   结束时间
   * @param dateName  统计日期名字
   * @return 游戏统计数据总和的列表
   */
  List<StatisticsGameDataBO> selectCorrectTotalSumAndErrorTotalSumAndSudokuLevelIdByDateBetweenAndDateName(
      @Param("startDate") LocalDate startDate,
      @Param("endDate") LocalDate endDate, @Param("dateName") String dateName);

  /**
   * 查询在startDate后，统计日期名字为dateName的游戏总局数
   *
   * @return 游戏总局数
   */
  Integer selectGameTotalByDateAfterAndDateName(@Param("startDate") LocalDate startDate, @Param("dateName") String dateName);

  /**
   * 批量插入数据
   *
   * @param list 数据列表
   * @return 插入的行数
   */
  int insertList(@Param("list") List<StatisticsGame> list);
}