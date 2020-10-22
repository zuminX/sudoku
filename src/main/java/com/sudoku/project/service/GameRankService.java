package com.sudoku.project.service;

import com.sudoku.common.constant.enums.RankingType;
import com.sudoku.common.tools.page.Page;
import com.sudoku.project.model.bo.RankItemDataBO;
import com.sudoku.project.model.entity.UserGameInformation;

/**
 * 游戏排行业务层接口
 */
public interface GameRankService {

  /**
   * 初始化排行数据
   */
  void initRanking();

  /**
   * 获取排行数据
   *
   * @param rankingType     排行类型
   * @param sudokuLevelName 数独等级名称
   * @param page            当前查询页
   * @param pageSize        每页显示的条数
   * @return 排行项数据列表的分页信息
   */
  Page<RankItemDataBO> getRanking(RankingType rankingType, String sudokuLevelName, Integer page, Integer pageSize);

  /**
   * 获取当前用户指定排行的排名
   *
   * @param rankingType 排行类型
   * @return 若排名不存在或大于RANKING_NUMBER，则返回null；否则返回对应的排名
   */
  Long getCurrentUserRank(RankingType rankingType, String sudokuLevelName);

  /**
   * 根据用户游戏信息更新对应的排名
   *
   * @param userGameInformation 用户游戏信息对象
   */
  void updateCurrentUserRank(UserGameInformation userGameInformation);
}
