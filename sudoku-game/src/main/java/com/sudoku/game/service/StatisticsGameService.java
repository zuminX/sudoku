package com.sudoku.game.service;

import com.sudoku.common.annotation.ExtCacheable;
import com.sudoku.common.core.domain.StatisticsDateRange;
import com.sudoku.common.core.template.GetStatisticsDataTemplate;
import com.sudoku.game.mapper.SudokuRecordMapper;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * 统计游戏信息的业务层类
 */
@Service
public class StatisticsGameService {

  private final SudokuRecordMapper sudokuRecordMapper;

  public StatisticsGameService(SudokuRecordMapper sudokuRecordMapper) {
    this.sudokuRecordMapper = sudokuRecordMapper;
  }

  /**
   * 获取在[startDate,endDate)中的游戏局数
   *
   * @param dateRange 统计日期范围
   * @return 游戏局数
   */
  @ExtCacheable(value = "statisticsGameData", ttlOfDays = 1)
  public List<Integer> getGameTotal(StatisticsDateRange dateRange) {
    return new GetStatisticsDataTemplate<Integer>(dateRange).getData(sudokuRecordMapper::countByDateBetween);
  }

  /**
   * 获取系统游戏总局数
   *
   * @return 游戏总局数
   */
  @ExtCacheable(value = "gameTotal", ttlOfDays = 1)
  public Integer getGameTotal() {
    return sudokuRecordMapper.count();
  }
}
