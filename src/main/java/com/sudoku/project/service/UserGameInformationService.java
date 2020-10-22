package com.sudoku.project.service;

import com.sudoku.project.model.entity.UserGameInformation;
import com.sudoku.project.model.vo.UserGameInformationVO;
import java.util.List;

/**
 * 用户游戏信息业务层接口
 */
public interface UserGameInformationService {

  /**
   * 更新用户游戏信息
   *
   * @return 更新后的用户游戏信息
   */
  UserGameInformation updateUserGameInformation();

  /**
   * 获取用户游戏信息
   *
   * @return 用户游戏信息的显示层列表
   */
  List<UserGameInformationVO> getUserGameInformation();

  /**
   * 根据用户ID，获取其游戏信息
   *
   * @param userId 用户ID
   * @return 用户游戏信息的显示层列表
   */
  List<UserGameInformationVO> getUserGameInformationById(Integer userId);

  /**
   * 初始化用户游戏信息
   *
   * @param id 用户ID
   */
  void initUserGameInformation(Integer id);

}
