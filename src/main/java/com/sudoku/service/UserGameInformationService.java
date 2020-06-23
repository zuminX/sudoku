package com.sudoku.service;

import com.sudoku.model.vo.RankDataVO;
import com.sudoku.model.vo.UserGameInformationVO;
import java.util.List;
import org.springframework.cache.annotation.Cacheable;

/**
 * 用户游戏信息业务层接口
 */
public interface UserGameInformationService {

  /**
   * 更新用户游戏信息
   */
  void updateUserGameInformation();

  /**
   * 获取用户游戏信息
   *
   * @return 用户游戏信息的显示层列表
   */
  List<UserGameInformationVO> getUserGameInformation();

  /**
   * 获取排行数据列表
   *
   * @return 排行数据显示层列表
   */
  @Cacheable(value = "rankList", keyGenerator = "simpleKG")
  List<RankDataVO<?>> getRankList();

  /**
   * 初始化用户游戏信息
   *
   * @param id 用户ID
   */
  void initUserGameInformation(Integer id);
}
