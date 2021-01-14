package com.sudoku.project.service;

import com.sudoku.common.constant.enums.StatisticsDate;
import com.sudoku.project.core.GetStatisticsDataTemplate;
import com.sudoku.project.mapper.GameRecordMapper;
import java.time.LocalDate;
import java.util.List;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * 统计游戏信息的业务层类
 */
@Service
public class StatisticsGameService {

  private final GameRecordMapper gameRecordMapper;

  public StatisticsGameService(GameRecordMapper gameRecordMapper) {
    this.gameRecordMapper = gameRecordMapper;
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
    return new GetStatisticsDataTemplate<Integer>(startDate, endDate, date).getData(gameRecordMapper::countByDateBetween);
  }

  /**
   * 获取系统游戏总局数
   *
   * @return 游戏总局数
   */
  @Cacheable(value = "gameTotal", keyGenerator = "simpleKG")
  public Integer getGameTotal() {
    return gameRecordMapper.count();
  }
}
