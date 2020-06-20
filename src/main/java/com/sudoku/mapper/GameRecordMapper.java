package com.sudoku.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sudoku.model.entity.GameRecord;import com.sudoku.model.vo.GameRecordVO;import java.util.Date;import java.util.List;import org.apache.ibatis.annotations.Param;

public interface GameRecordMapper extends BaseMapper<GameRecord> {

  int insertSelective(GameRecord record);

  int updateEndTimeAndCorrectById(@Param("updatedEndTime") Date updatedEndTime, @Param("updatedCorrect") Boolean updatedCorrect,
      @Param("id") Integer id);

  List<GameRecordVO> findByUidOrderByStartTimeDesc(@Param("uid") Integer uid);
}