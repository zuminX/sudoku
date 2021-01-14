package com.sudoku.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sudoku.project.model.bo.RankItemBO;
import com.sudoku.project.model.entity.UserGameInformation;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 用户游戏信息持久层类
 */
public interface UserGameInformationMapper extends BaseMapper<UserGameInformation> {

  /**
   * 根据用户ID查询其游戏信息
   *
   * @param userId 用户ID
   * @return 该用户的游戏信息
   */
  List<UserGameInformation> selectByUserId(@Param("userId") Integer userId);

  /**
   * 根据用户ID和数独等级ID查询游戏信息
   *
   * @param userId        用户ID
   * @param sudokuLevelId 数独等级ID
   * @return 该用户的指定等级的游戏信息
   */
  UserGameInformation selectByUserIdAndSudokuLevelId(@Param("userId") Integer userId, @Param("sudokuLevelId") Integer sudokuLevelId);

  /**
   * 根据用户ID插入默认的游戏信息
   *
   * @param userId 用户ID
   * @return 影响行数
   */
  int insertDefaultByUserId(@Param("userId") Integer userId);

  /**
   * 根据用户ID和数独等级ID更新游戏信息
   *
   * @param updated       待更新的游戏信息
   * @param userId        用户ID
   * @param sudokuLevelId 数独等级ID
   * @return 影响行数
   */
  int updateByUserIdAndSudokuLevelId(@Param("updated") UserGameInformation updated, @Param("userId") Integer userId,
      @Param("sudokuLevelId") Integer sudokuLevelId);

  /**
   * 查询平均花费时间的排名
   *
   * @param limitNumber 最多的排名数
   * @return 排行项列表
   */
  List<RankItemBO> selectAverageSpendTimeRanking(@Param("limitNumber") Integer limitNumber);

  /**
   * 查询最少花费时间的排名
   *
   * @param limitNumber 最多的排名数
   * @return 排行项列表
   */
  List<RankItemBO> selectMinSpendTimeRanking(@Param("limitNumber") Integer limitNumber);

  /**
   * 查询回答正确数的排名
   *
   * @param limitNumber 最多的排名数
   * @return 排行项列表
   */
  List<RankItemBO> selectCorrectNumberRanking(@Param("limitNumber") Integer limitNumber);
}