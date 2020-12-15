package com.sudoku.project.service;

import com.sudoku.common.log.BusinessType;
import com.sudoku.common.log.Log;
import com.sudoku.common.utils.PublicUtils;
import com.sudoku.common.utils.SecurityUtils;
import com.sudoku.project.convert.UserGameInformationConvert;
import com.sudoku.project.mapper.SudokuLevelMapper;
import com.sudoku.project.mapper.UserGameInformationMapper;
import com.sudoku.project.model.bo.GameRecordBO;
import com.sudoku.project.model.entity.UserGameInformation;
import com.sudoku.project.model.vo.UserGameInformationVO;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户游戏信息业务层类
 */
@Service
public class UserGameInformationService {

  private final UserGameInformationMapper userGameInformationMapper;

  private final SudokuLevelMapper sudokuLevelMapper;

  private final UserGameInformationConvert userGameInformationConvert;

  public UserGameInformationService(
      UserGameInformationMapper userGameInformationMapper, SudokuLevelMapper sudokuLevelMapper,
      UserGameInformationConvert userGameInformationConvert) {
    this.userGameInformationMapper = userGameInformationMapper;
    this.sudokuLevelMapper = sudokuLevelMapper;
    this.userGameInformationConvert = userGameInformationConvert;
  }

  /**
   * 更新用户游戏信息
   *
   * @param gameRecordBO 游戏记录
   * @return 更新后的用户游戏信息
   */
  @Transactional
  @Log(value = "更新用户游戏信息", businessType = BusinessType.UPDATE)
  public UserGameInformation updateUserGameInformation(GameRecordBO gameRecordBO) {
    UserGameInformation information = userGameInformationMapper.selectByUserIdAndSudokuLevelId(gameRecordBO.getUserId(),
        gameRecordBO.getSudokuLevelId());
    updateInformation(gameRecordBO, information);
    return information;
  }

  /**
   * 获取用户游戏信息
   *
   * @return 用户游戏信息的显示层列表
   */
  public List<UserGameInformationVO> getUserGameInformation() {
    return getUserGameInformation(SecurityUtils.getCurrentUserId());
  }

  /**
   * 根据用户ID，获取其游戏信息
   *
   * @param userId 用户ID
   * @return 用户游戏信息的显示层列表
   */
  public List<UserGameInformationVO> getUserGameInformationById(Integer userId) {
    return getUserGameInformation(userId);
  }

  /**
   * 初始化用户游戏信息
   *
   * @param id 用户ID
   */
  @Transactional
  @Log("初始化用户游戏信息")
  public void initUserGameInformation(Integer id) {
    userGameInformationMapper.insertDefaultByUserId(id);
  }

  /**
   * 获取指定用户的游戏信息
   *
   * @param userId 用户ID
   * @return 用户游戏信息的显示层列表
   */
  private List<UserGameInformationVO> getUserGameInformation(@NotNull Integer userId) {
    List<UserGameInformation> userGameInformationList = userGameInformationMapper.selectByUserId(userId);
    return convertToUserGameInformationVOList(userGameInformationList);
  }

  /**
   * 将用户游戏信息持久层对象列表转换为显示层对象列表
   *
   * @param userGameInformationList 用户游戏信息持久层对象列表
   * @return 用户游戏信息显示层对象列表
   */
  private List<UserGameInformationVO> convertToUserGameInformationVOList(List<UserGameInformation> userGameInformationList) {
    List<UserGameInformationVO> list = new ArrayList<>();
    Map<String, String> idToName = sudokuLevelMapper.selectIdToName();
    userGameInformationList.forEach(userGameInformation -> {
      UserGameInformationVO convert = userGameInformationConvert.convert(userGameInformation);
      convert.setSudokuLevelName(idToName.get(userGameInformation.getSudokuLevelId().toString()));
      list.add(convert);
    });
    return list;
  }

  /**
   * 根据游戏记录更新信息
   *
   * @param gameRecord  游戏记录
   * @param information 用户游戏信息
   */
  private void updateInformation(GameRecordBO gameRecord, UserGameInformation information) {
    int spendTime = getThisBoardSpendTime(gameRecord);

    information.setTotal(information.getTotal() + 1);
    information.setAverageSpendTime(computeAverageSpendTime(spendTime, information));
    information.setMinSpendTime(computeMinSpendTime(spendTime, information.getMinSpendTime()));
    information.setMaxSpendTime(computeMaxSpendTime(spendTime, information.getMaxSpendTime()));
    information.setCorrectNumber(information.getCorrectNumber() + 1);

    userGameInformationMapper.updateByUserIdAndSudokuLevelId(information, gameRecord.getUserId(), gameRecord.getSudokuLevelId());
  }

  /**
   * 计算用户信息中的平均花费时间
   *
   * @param spendTime   本局游戏花费的时间
   * @param information 用户游戏信息
   * @return 平均花费时间
   */
  private int computeAverageSpendTime(int spendTime, UserGameInformation information) {
    if (isFirstGame(information.getAverageSpendTime())) {
      return spendTime;
    }
    int correctNumber = information.getCorrectNumber();
    long totalAverageSpendTime = (long) information.getAverageSpendTime() * correctNumber;
    return Math.toIntExact(((totalAverageSpendTime + spendTime) / (correctNumber + 1)));
  }

  /**
   * 计算用户信息中的最少花费时间
   *
   * @param spendTime    本局游戏花费的时间
   * @param minSpendTime 用户之前最少花费时间
   * @return 最少花费时间
   */
  private int computeMinSpendTime(int spendTime, Integer minSpendTime) {
    return isFirstGame(minSpendTime) || minSpendTime > spendTime ? spendTime : minSpendTime;
  }

  /**
   * 计算用户信息中的最大花费时间
   *
   * @param spendTime    本局游戏花费的时间
   * @param maxSpendTime 用户之前最大花费时间
   * @return 最大花费时间
   */
  private int computeMaxSpendTime(int spendTime, Integer maxSpendTime) {
    return isFirstGame(maxSpendTime) || maxSpendTime < spendTime ? spendTime : maxSpendTime;
  }

  /**
   * 获取本局花费的时间
   *
   * @param gameRecord 游戏记录
   * @return 花费的时间
   */
  private int getThisBoardSpendTime(GameRecordBO gameRecord) {
    return Math.toIntExact(PublicUtils.computeAbsDiff(gameRecord.getEndTime(), gameRecord.getStartTime()));
  }

  /**
   * 是否为第一次游戏
   *
   * @param spendTime 花费的时间
   * @return 是第一次游戏返回true，否则返回false
   */
  private boolean isFirstGame(Integer spendTime) {
    return spendTime == null;
  }
}
