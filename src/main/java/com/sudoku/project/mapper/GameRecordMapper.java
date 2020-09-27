package com.sudoku.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sudoku.project.model.bo.StatisticsGameDataBO;
import com.sudoku.project.model.entity.GameRecord;
import com.sudoku.project.model.vo.GameRecordVO;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GameRecordMapper extends BaseMapper<GameRecord> {

  int insertSelective(GameRecord record);

  int updateEndTimeAndCorrectById(@Param("endTime") LocalDateTime endTime, @Param("correct") Boolean correct, @Param("id") Integer id);

  List<GameRecordVO> findByUidOrderByStartTimeDesc(@Param("userId") Integer userId);

  /**
   * 获取系统中最早结束的数独游戏的时间
   *
   * @return 最早结束的数独游戏的时间
   */
  LocalDateTime findFirstEndTimeOrderByEndTime();

  /**
   * 统计在[startDate,endDate)中游戏统计数据列表
   *
   * @param startDate 开始时间
   * @param endDate   结束时间
   * @return 游戏统计数据列表
   */
  List<StatisticsGameDataBO> countCorrectTotalAndErrorTotalSudokuLevelIdByEndTimeBetween(@Param("startDate") LocalDate startDate,
      @Param("endDate") LocalDate endDate);
}