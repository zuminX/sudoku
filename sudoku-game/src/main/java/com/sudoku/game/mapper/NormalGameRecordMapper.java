package com.sudoku.game.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sudoku.common.rank.RankItemBO;
import com.sudoku.game.model.entity.NormalGameRecord;
import com.sudoku.game.model.result.NormalGameRecordResultForHistory;
import com.sudoku.game.model.vo.UserGameInformationVO;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 普通游戏记录持久层类
 */
public interface NormalGameRecordMapper extends BaseMapper<NormalGameRecord> {

  /**
   * 根据用户ID查找其游戏记录，且结果按开始时间降序排序
   *
   * @param userId 用户ID
   * @return 查询历史普通游戏记录结果的列表
   */
  List<NormalGameRecordResultForHistory> findByUserIdOrderByStartTimeDesc(@Param("userId") Integer userId);

  /**
   * 根据用户ID查找其不属于指定数独记录ID的游戏记录，且结果按开始时间降序排序
   *
   * @param userId               用户ID
   * @param ignoreSudokuRecordId 排除的数独记录ID
   * @return 查询历史普通游戏记录结果的列表
   */
  List<NormalGameRecordResultForHistory> findByUserIdOrderByStartTimeDescIgnoreOneSudokuRecord(@Param("userId") Integer userId,
      @Param("ignoreSudokuRecordId") Integer ignoreSudokuRecordId);

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

  /**
   * 根据用户ID查询其游戏信息
   *
   * @param userId 用户ID
   * @return 该用户的游戏信息
   */
  List<UserGameInformationVO> selectGameInformationByUserId(Integer userId);

  /**
   * 更新指定数独记录的游戏记录中的输入矩阵和回答情况
   *
   * @param inputMatrix     输入矩阵
   * @param answerSituation 回答情况
   * @param sudokuRecordId  数独记录ID
   * @return 影响行数
   */
  int updateInputMatrixAndAnswerSituationBySudokuRecordId(@Param("inputMatrix") String inputMatrix,
      @Param("answerSituation") Integer answerSituation, @Param("sudokuRecordId") Integer sudokuRecordId);
}