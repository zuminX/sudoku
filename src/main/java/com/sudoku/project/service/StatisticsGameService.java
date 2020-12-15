package com.sudoku.project.service;

import static java.util.stream.Collectors.toList;

import com.sudoku.common.constant.consist.RedisKeys;
import com.sudoku.common.constant.enums.StatisticsDate;
import com.sudoku.common.tools.DataStamped;
import com.sudoku.common.utils.project.SudokuLevelUtils;
import com.sudoku.project.convert.StatisticsGameConvert;
import com.sudoku.project.core.UpdateOutDatedDataInRedis;
import com.sudoku.project.core.UpdateStatisticsData;
import com.sudoku.project.mapper.GameRecordMapper;
import com.sudoku.project.mapper.StatisticsGameMapper;
import com.sudoku.project.model.bo.StatisticsGameDataBO;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 统计游戏信息的业务层类
 */
@Service
public class StatisticsGameService {

  private final StatisticsGameMapper statisticsGameMapper;

  private final GameRecordMapper gameRecordMapper;

  private final SudokuLevelUtils sudokuLevelUtils;

  private final StatisticsGameConvert statisticsGameConvert;

  public StatisticsGameService(StatisticsGameMapper statisticsGameMapper,
      GameRecordMapper gameRecordMapper, SudokuLevelUtils sudokuLevelUtils, StatisticsGameConvert statisticsGameConvert) {
    this.statisticsGameMapper = statisticsGameMapper;
    this.gameRecordMapper = gameRecordMapper;
    this.sudokuLevelUtils = sudokuLevelUtils;
    this.statisticsGameConvert = statisticsGameConvert;
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
    return statisticsGameMapper.selectGameTotalByDateBetweenAndDateName(startDate, endDate, date.getName());
  }

  /**
   * 获取系统游戏总局数
   *
   * @return 游戏总局数
   */
  public Integer getGameTotal() {
    UpdateOutDatedDataInRedis<Integer> operate = new UpdateOutDatedDataInRedis<>(RedisKeys.GAME_TOTAL);
    return operate.updateData((DataStamped<Integer> oldData) -> {
      Integer newGameTotal = statisticsGameMapper.selectGameTotalByDateAfterAndDateName(oldData.getUpdateDate().plusDays(1L),
          StatisticsDate.DAILY.getName()).orElse(0);
      return oldData.getData() + newGameTotal;
    }, () -> statisticsGameMapper.selectGameTotalByDateName(StatisticsDate.DAILY.getName()).orElse(0));
  }

  /**
   * 更新每日的统计信息，直到当前
   */
  public void updateDailyDataUntilNow() {
    UpdateGameStatisticsData updateGameStatisticsData = new UpdateGameStatisticsData(StatisticsDate.DAILY);
    updateGameStatisticsData.updateData(gameRecordMapper::countCorrectTotalAndErrorTotalSudokuLevelIdByEndTimeBetween);
  }

  /**
   * 更新每月的统计信息，直到当前
   */
  public void updateEachMonthDataUntilNow() {
    UpdateGameStatisticsData updateGameStatisticsData = new UpdateGameStatisticsData(StatisticsDate.EACH_MONTH);
    updateGameStatisticsData.updateData(
        (LocalDate startDate, LocalDate endDate) -> statisticsGameMapper.selectCorrectTotalSumAndErrorTotalSumAndSudokuLevelIdByDateBetweenAndDateName(
            startDate, endDate, StatisticsDate.DAILY.getName()));
  }

  /**
   * 更新游戏统计数据的模板方法
   */
  private class UpdateGameStatisticsData extends UpdateStatisticsData<List<StatisticsGameDataBO>> {

    /**
     * 该类的构造方法
     *
     * @param statisticsDate 统计日期对象
     */
    public UpdateGameStatisticsData(StatisticsDate statisticsDate) {
      super(statisticsDate);
    }

    /**
     * 向数据库插入游戏统计数据
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @param callback  获取游戏的统计数据列表的回调方法
     */
    @Override
    @Transactional
    protected void insertData(LocalDate startDate, LocalDate endDate, StatisticsDataCallback<List<StatisticsGameDataBO>> callback) {
      List<StatisticsGameDataBO> statisticsGameData = callback.getStaticsData(startDate, endDate);
      if (statisticsGameData == null) {
        statisticsGameData = new ArrayList<>();
      }
      addLackData(statisticsGameData);
      statisticsGameMapper.insertList(statisticsGameData.stream()
          .map(data -> statisticsGameConvert.convert(data, getStatisticsDate().getName(), startDate))
          .collect(toList()));
    }

    /**
     * 添加缺失的数据，以保证数据的完整性
     *
     * @param gameDataBOList 游戏统计数据对象列表
     */
    private void addLackData(List<StatisticsGameDataBO> gameDataBOList) {
      List<Integer> lackSudokuLevelId = sudokuLevelUtils.findLackId(
          gameDataBOList.stream().map(StatisticsGameDataBO::getSudokuLevelId).collect(toList()));
      gameDataBOList.addAll(lackSudokuLevelId.stream().map(StatisticsGameDataBO::getZero).collect(toList()));
    }

    /**
     * 获取最新统计数据的日期
     *
     * @return 第一次统计数据的日期
     */
    @Override
    protected LocalDate getLatestUpdateDate() {
      return statisticsGameMapper.findFirstDateByDateNameOrderByDateDesc(getStatisticsDate().getName());
    }

    /**
     * 使用系统中最早结束的数独游戏的时间
     *
     * @return 最早结束的数独游戏的时间
     */
    @Override
    protected LocalDate getFirstStatisticsDate() {
      return gameRecordMapper.findFirstEndTimeOrderByEndTime().orElse(LocalDateTime.now()).toLocalDate();
    }
  }

}
