package com.sudoku.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sudoku.project.model.bo.RankItemBO;
import com.sudoku.project.model.entity.NormalGameRecord;
import com.sudoku.project.model.result.NormalGameRecordResultForHistory;
import com.sudoku.project.model.vo.UserGameInformationVO;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NormalGameRecordMapper extends BaseMapper<NormalGameRecord> {

  /**
   * 根据用户ID查找其游戏记录，且结果按开始时间降序排序
   *
   * @param userId 用户ID
   * @return 查询历史普通游戏记录结果的列表
   */
  List<NormalGameRecordResultForHistory> findByUserIdOrderByStartTimeDesc(@Param("userId") Integer userId);

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
}