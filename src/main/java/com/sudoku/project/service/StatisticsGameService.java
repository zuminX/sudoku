package com.sudoku.project.service;

import com.sudoku.common.constant.enums.StatisticsDate;
import java.time.LocalDate;
import java.util.List;
import org.springframework.cache.annotation.Cacheable;

public interface StatisticsGameService {

  @Cacheable(value = "statisticsGameData", keyGenerator = "simpleKG")
  List<Integer> getGameTotal(LocalDate startDate, LocalDate endDate, StatisticsDate date);

  Integer getGameTotal();

  void updateDailyDataUntilNow();

  void updateEachMonthDataUntilNow();
}
