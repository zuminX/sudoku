package com.sudoku.mapper;

import com.sudoku.model.entity.GameRecord;
import com.sudoku.model.vo.GameRecordVO;
import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 游戏记录持久层
 */
public interface GameRecordMapper {

  int deleteByPrimaryKey(Integer id);

  int insert(GameRecord record);

  int insertSelective(GameRecord record);

  GameRecord selectByPrimaryKey(Integer id);

  int updateByPrimaryKeySelective(GameRecord record);

  int updateByPrimaryKey(GameRecord record);

  int updateEndTimeAndCorrectById(@Param("updatedEndTime") Date updatedEndTime, @Param("updatedCorrect") Boolean updatedCorrect,
      @Param("id") Integer id);

  List<GameRecordVO> findByUidOrderByStartTimeDesc(@Param("uid")Integer uid);
}