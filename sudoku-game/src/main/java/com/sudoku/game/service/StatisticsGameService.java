package com.sudoku.game.service;

import com.sudoku.common.constant.enums.StatisticsDate;
import com.sudoku.common.core.template.GetStatisticsDataTemplate;
import com.sudoku.game.mapper.SudokuRecordMapper;
import java.time.LocalDate;
import java.util.List;
import org.springframework.cache.annotation.Cacheable;
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
   * @param startDate 开始日期
   * @param endDate   结束日期
   * @param date      统计日期
   * @return 游戏局数
   */
  @Cacheable(value = "statisticsGameData", keyGenerator = "simpleKG")
  public List<Integer> getGameTotal(LocalDate startDate, LocalDate endDate, StatisticsDate date) {
    return new GetStatisticsDataTemplate<Integer>(startDate, endDate, date).getData(sudokuRecordMapper::countByDateBetween);
  }

  /**
   * 获取系统游戏总局数
   *
   * @return 游戏总局数
   */
  @Cacheable(value = "gameTotal", keyGenerator = "simpleKG")
  public Integer getGameTotal() {
    return sudokuRecordMapper.count();
  }
}
