package com.sudoku.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sudoku.project.model.entity.GameRecord;
import com.sudoku.project.model.vo.GameRecordVO;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Param;

public interface GameRecordMapper extends BaseMapper<GameRecord> {

  int insertSelective(GameRecord record);

  List<GameRecordVO> findByUserIdAndCorrectNotNullOrderByStartTimeDesc(@Param("userId") Integer userId);

  /**
   * 获取系统中最早结束的数独游戏的时间
   *
   * @return 最早结束的数独游戏的时间
   */
  Optional<LocalDateTime> findFirstEndTimeOrderByEndTime();

  /**
   * 获取系统的游戏总数
   *
   * @return 游戏总数
   */
  Integer count();

  /**
   * 统计在[startDate,endDate)中游戏总数
   *
   * @param startDate 开始时间
   * @param endDate   结束时间
   * @return 游戏总数
   */
  Integer countByDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}