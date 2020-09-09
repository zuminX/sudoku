package com.sudoku.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sudoku.model.bo.RankItemBO;
import com.sudoku.model.entity.UserGameInformation;
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

  List<RankItemBO<Integer>> selectAverageSpendTimeRanking(@Param("limitNumber") Integer limitNumber);

  List<RankItemBO<Integer>> selectMinSpendTimeRanking(@Param("limitNumber") Integer limitNumber);

  List<RankItemBO<Integer>> selectCorrectNumberRanking(@Param("limitNumber") Integer limitNumber);
}