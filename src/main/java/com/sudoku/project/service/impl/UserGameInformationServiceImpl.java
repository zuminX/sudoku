package com.sudoku.project.service.impl;

import com.sudoku.common.constant.consist.RankDataName;
import com.sudoku.common.constant.consist.SettingParameter;
import com.sudoku.common.log.BusinessType;
import com.sudoku.common.log.Log;
import com.sudoku.common.utils.GameUtils;
import com.sudoku.common.utils.PublicUtils;
import com.sudoku.common.utils.SecurityUtils;
import com.sudoku.project.convert.RankDataConvert;
import com.sudoku.project.convert.UserGameInformationConvert;
import com.sudoku.project.mapper.SudokuLevelMapper;
import com.sudoku.project.mapper.UserGameInformationMapper;
import com.sudoku.project.model.bo.GameRecordBO;
import com.sudoku.project.model.bo.RankItemBO;
import com.sudoku.project.model.entity.SudokuLevel;
import com.sudoku.project.model.entity.UserGameInformation;
import com.sudoku.project.model.vo.RankDataVO;
import com.sudoku.project.model.vo.UserGameInformationVO;
import com.sudoku.project.service.UserGameInformationService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户游戏信息业务层实现类
 */
@Service
public class UserGameInformationServiceImpl implements UserGameInformationService {

  private final UserGameInformationMapper userGameInformationMapper;
  private final SudokuLevelMapper sudokuLevelMapper;
  private final GameUtils gameUtils;
  private final RankDataConvert rankDataConvert;
  private final UserGameInformationConvert userGameInformationConvert;

  public UserGameInformationServiceImpl(
      UserGameInformationMapper userGameInformationMapper, SudokuLevelMapper sudokuLevelMapper, GameUtils gameUtils,
      RankDataConvert rankDataConvert, UserGameInformationConvert userGameInformationConvert) {
    this.userGameInformationMapper = userGameInformationMapper;
    this.sudokuLevelMapper = sudokuLevelMapper;
    this.gameUtils = gameUtils;
    this.rankDataConvert = rankDataConvert;
    this.userGameInformationConvert = userGameInformationConvert;
  }

  /**
   * 更新用户游戏信息
   */
  @Override
  @Transactional
  @Log(value = "更新用户游戏信息", businessType = BusinessType.UPDATE)
  public void updateUserGameInformation() {
    GameRecordBO gameRecord = gameUtils.getGameRecord();
    UserGameInformation information = userGameInformationMapper.selectByUserIdAndSudokuLevelId(gameRecord.getUserId(),
        gameRecord.getSudokuLevelId());
    if (GameUtils.isGameStart(gameRecord)) {
      plusUserGameTotal(gameRecord, information.getTotal());
      return;
    }
    if (gameRecord.getCorrect()) {
      updateInformation(gameRecord, information);
    }
  }

  /**
   * 获取用户游戏信息
   *
   * @return 用户游戏信息的显示层列表
   */
  @Override
  @Transactional
  public List<UserGameInformationVO> getUserGameInformation() {
    //获取当前用户的游戏信息
    Integer userId = SecurityUtils.getUserId();
    List<UserGameInformation> userGameInformationList = userGameInformationMapper.selectByUserId(userId);
    //获取数独级别列表
    List<SudokuLevel> sudokuLevels = sudokuLevelMapper.selectAll();
    //若当前用户的游戏信息比数独级别数少，则缺少了信息
    if (sudokuLevels.size() > userGameInformationList.size()) {
      insertUserLackSudokuLevelInformation(userGameInformationList, sudokuLevels, userId);
      userGameInformationList = userGameInformationMapper.selectByUserId(userId);
    }
    return convertToUserGameInformationVOList(userGameInformationList);
  }

  /**
   * 获取排行数据列表
   *
   * @return 排行数据显示层列表
   */
  @Override
  @Cacheable(value = "rankList", keyGenerator = "simpleKG")
  public List<RankDataVO<?>> getRankList() {
    List<RankDataVO<?>> rankList = new ArrayList<>(3);
    rankList.add(getCorrectNumberRankingList());
    rankList.add(getMinSpendTimeRankingList());
    rankList.add(getAverageSpendTimeRankingList());
    return rankList;
  }

  /**
   * 初始化用户游戏信息
   *
   * @param id 用户ID
   */
  @Override
  @Transactional
  @Log("初始化用户游戏信息")
  public void initUserGameInformation(Integer id) {
    userGameInformationMapper.insertDefaultByUserId(id);
  }

  /**
   * 插入用户缺少的数独级别信息
   *
   * @param userGameInformationList 用户游戏信息列表
   * @param sudokuLevels            数独级别
   * @param userId                  用户ID
   */
  @Log(value = "插入用户缺少的数独等级信息", isSaveParameterData = false)
  private void insertUserLackSudokuLevelInformation(List<UserGameInformation> userGameInformationList, List<SudokuLevel> sudokuLevels,
      Integer userId) {
    Set<Integer> slids = userGameInformationList.stream().map(UserGameInformation::getSudokuLevelId).collect(Collectors.toSet());
    List<Integer> lackSlids = new ArrayList<>(sudokuLevels.size() - userGameInformationList.size());
    sudokuLevels.forEach(sudokuLevel -> {
      Integer slid = sudokuLevel.getId();
      if (!slids.contains(slid)) {
        lackSlids.add(slid);
      }
    });
    userGameInformationMapper.batchInsertByUserIdAndSudokuLevelIds(userId, lackSlids);
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
   * 获取以回答正确个数排名的列表
   *
   * @return 排行数据显示层
   */
  private RankDataVO<Integer> getCorrectNumberRankingList() {
    List<RankItemBO<Integer>> list = userGameInformationMapper.selectCorrectNumberRanking(SettingParameter.RANKING_NUMBER);
    return rankDataConvert.convert(list, RankDataName.CORRECT_NUMBER);
  }

  /**
   * 获取以最少用时排名的列表
   *
   * @return 排行数据显示层
   */
  private RankDataVO<Integer> getMinSpendTimeRankingList() {
    List<RankItemBO<Integer>> list = userGameInformationMapper.selectMinSpendTimeRanking(SettingParameter.RANKING_NUMBER);
    return rankDataConvert.convert(list, RankDataName.MIN_SPEND_TIME);
  }

  /**
   * 获取以平均用时排名的列表
   *
   * @return 排行数据显示层
   */
  private RankDataVO<Integer> getAverageSpendTimeRankingList() {
    List<RankItemBO<Integer>> list = userGameInformationMapper.selectAverageSpendTimeRanking(SettingParameter.RANKING_NUMBER);
    return rankDataConvert.convert(list, RankDataName.AVERAGE_SPEND_TIME);
  }

  /**
   * 根据游戏记录更新信息
   *
   * @param gameRecord  游戏记录
   * @param information 用户游戏信息
   */
  private void updateInformation(GameRecordBO gameRecord, UserGameInformation information) {
    int spendTime = getThisBoardSpendTime(gameRecord);

    information.setAverageSpendTime(computeAverageSpendTime(spendTime, information));
    information.setMinSpendTime(computeMinSpendTime(spendTime, information.getMinSpendTime()));
    information.setMaxSpendTime(computeMaxSpendTime(spendTime, information.getMaxSpendTime()));
    information.setCorrectNumber(information.getCorrectNumber() + 1);

    userGameInformationMapper.updateByUserIdAndSudokuLevelId(information, gameRecord.getUserId(), gameRecord.getSudokuLevelId());
  }

  /**
   * 增加用户游戏总数
   *
   * @param gameRecord 游戏记录
   * @param total      用户之前总数
   */
  private void plusUserGameTotal(GameRecordBO gameRecord, Integer total) {
    userGameInformationMapper.updateTotalByUserIdAndSudokuLevelId(total + 1, gameRecord.getUserId(), gameRecord.getSudokuLevelId());
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
    return Math.toIntExact(PublicUtils.getTwoDateAbsDiff(gameRecord.getEndTime(), gameRecord.getStartTime()));
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
