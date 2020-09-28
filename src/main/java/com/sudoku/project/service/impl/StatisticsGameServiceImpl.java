package com.sudoku.project.service.impl;

import static java.util.stream.Collectors.toList;

import com.sudoku.common.constant.consist.RedisKeys;
import com.sudoku.common.constant.enums.StatisticsDate;
import com.sudoku.common.constant.enums.StatusCode;
import com.sudoku.common.exception.StatisticsException;
import com.sudoku.common.tools.DataStamped;
import com.sudoku.common.utils.SudokuLevelUtils;
import com.sudoku.project.convert.StatisticsGameConvert;
import com.sudoku.project.core.UpdateOutDatedDataInRedis;
import com.sudoku.project.core.UpdateStatisticsData;
import com.sudoku.project.mapper.GameRecordMapper;
import com.sudoku.project.mapper.StatisticsGameMapper;
import com.sudoku.project.model.bo.StatisticsGameDataBO;
import com.sudoku.project.service.StatisticsGameService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 统计游戏信息的业务层实现类
 */
@Service
public class StatisticsGameServiceImpl implements StatisticsGameService {

  @Autowired
  private StatisticsGameMapper statisticsGameMapper;
  @Autowired
  private GameRecordMapper gameRecordMapper;
  @Autowired
  private SudokuLevelUtils sudokuLevelUtils;

  /**
   * 获取在[startDate,endDate)中的游戏局数
   *
   * @param startDate 开始日期
   * @param endDate   结束日期
   * @param date      统计日期
   * @return 游戏局数
   */
  @Override
  @Cacheable(value = "statisticsGameData", keyGenerator = "simpleKG")
  public List<Integer> getGameTotal(LocalDate startDate, LocalDate endDate, StatisticsDate date) {
    if (startDate.compareTo(endDate) > 0) {
      throw new StatisticsException(StatusCode.STATISTICS_INQUIRY_DATE_INVALID);
    }
    return statisticsGameMapper.selectGameTotalByDateBetweenAndDateName(startDate, endDate, date.getName());
  }

  /**
   * 获取系统游戏总局数
   *
   * @return 游戏总局数
   */
  @Override
  public Integer getGameTotal() {
    return new UpdateOutDatedDataInRedis<Integer>(RedisKeys.GAME_TOTAL) {
      @Override
      public Integer getLatestDataIfEmpty() {
        return statisticsGameMapper.selectGameTotalByDateName(StatisticsDate.DAILY.getName()).orElse(0);
      }

      @Override
      public Integer getLatestData(DataStamped<Integer> oldData) {
        Integer newGameTotal = statisticsGameMapper.selectGameTotalByDateAfterAndDateName(oldData.getUpdateDate().plusDays(1L),
            StatisticsDate.DAILY.getName()).orElse(0);
        return oldData.getData() + newGameTotal;
      }
    }.updateData();
  }

  /**
   * 更新每日的统计信息，直到当前
   */
  @Override
  public void updateDailyDataUntilNow() {
    new UpdateGameStatisticsData(StatisticsDate.DAILY) {
      @Override
      public List<StatisticsGameDataBO> getStatisticsGameData(LocalDate startDate, LocalDate endDate) {
        return gameRecordMapper.countCorrectTotalAndErrorTotalSudokuLevelIdByEndTimeBetween(startDate, endDate);
      }
    }.updateData();
  }

  /**
   * 更新每月的统计信息，直到当前
   */
  @Override
  public void updateEachMonthDataUntilNow() {
    new UpdateGameStatisticsData(StatisticsDate.EACH_MONTH) {
      @Override
      public List<StatisticsGameDataBO> getStatisticsGameData(LocalDate startDate, LocalDate endDate) {
        return statisticsGameMapper.selectCorrectTotalSumAndErrorTotalSumAndSudokuLevelIdByDateBetweenAndDateName(startDate, endDate,
            StatisticsDate.DAILY.getName());
      }
    }.updateData();
  }

  /**
   * 更新游戏统计数据的模板方法
   */
  private abstract class UpdateGameStatisticsData extends UpdateStatisticsData {

    /**
     * 该类的构造方法
     *
     * @param statisticsDate 统计日期对象
     */
    public UpdateGameStatisticsData(StatisticsDate statisticsDate) {
      super(statisticsDate);
    }

    /**
     * 获取待插入数据库的游戏统计数据列表
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 游戏的统计数据列表
     */
    public abstract List<StatisticsGameDataBO> getStatisticsGameData(LocalDate startDate, LocalDate endDate);

    /**
     * 向数据库插入游戏统计数据
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     */
    @Override
    @Transactional
    protected void insertData(LocalDate startDate, LocalDate endDate) {
      List<StatisticsGameDataBO> statisticsGameData = getStatisticsGameData(startDate, endDate);
      addLackData(statisticsGameData);
      statisticsGameMapper.insertList(statisticsGameData.stream()
          .map(data -> StatisticsGameConvert.INSTANCE.convert(data, getStatisticsDate().getName(), startDate))
          .collect(toList()));
    }

    /**
     * 添加缺失的数据，以保证数据的完整性
     *
     * @param gameDataBOList 游戏统计数据对象列表
     */
    private void addLackData(List<StatisticsGameDataBO> gameDataBOList) {
      if (gameDataBOList == null) {
        gameDataBOList = new ArrayList<>();
      }
      List<Integer> lackSudokuLevelId = sudokuLevelUtils.findLackSudokuLevelId(
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
