package com.sudoku.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sudoku.project.model.bo.RankItemBO;
import com.sudoku.project.model.entity.UserGameInformation;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserGameInformationMapper extends BaseMapper<UserGameInformation> {

  List<UserGameInformation> selectByUserId(@Param("userId") Integer userId);

  UserGameInformation selectByUserIdAndSudokuLevelId(@Param("userId") Integer userId, @Param("sudokuLevelId") Integer sudokuLevelId);

  int batchInsertByUserIdAndSudokuLevelIds(@Param("userId") Integer userId, @Param("sudokuLevelIds") List<Integer> sudokuLevelIds);

  int insertDefaultByUserId(@Param("userId") Integer userId);

  int updateByUserIdAndSudokuLevelId(@Param("updated") UserGameInformation updated, @Param("userId") Integer userId,
      @Param("sudokuLevelId") Integer sudokuLevelId);

  int updateTotalByUserIdAndSudokuLevelId(@Param("updatedTotal") Integer updatedTotal, @Param("userId") Integer userId,
      @Param("sudokuLevelId") Integer sudokuLevelId);

  List<RankItemBO> selectAverageSpendTimeRanking(@Param("limitNumber") Integer limitNumber);

  List<RankItemBO> selectMinSpendTimeRanking(@Param("limitNumber") Integer limitNumber);

  List<RankItemBO> selectCorrectNumberRanking(@Param("limitNumber") Integer limitNumber);
}