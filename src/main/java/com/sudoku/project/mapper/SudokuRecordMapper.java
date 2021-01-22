package com.sudoku.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sudoku.project.model.entity.SudokuRecord;
import java.time.LocalDate;
import org.apache.ibatis.annotations.Param;

public interface SudokuRecordMapper extends BaseMapper<SudokuRecord> {

  /**
   * 统计在[startDate,endDate)中游戏总数
   *
   * @param startDate 开始时间
   * @param endDate   结束时间
   * @return 游戏总数
   */
  Integer countByDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

  /**
   * 获取系统的游戏总数
   *
   * @return 游戏总数
   */
  Integer count();
}