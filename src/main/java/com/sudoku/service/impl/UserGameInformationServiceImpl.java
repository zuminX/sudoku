package com.sudoku.service.impl;

import static com.sudoku.constant.consist.SessionKey.GAME_RECORD_KEY;

import com.sudoku.constant.consist.RankDataName;
import com.sudoku.constant.consist.SettingParameter;
import com.sudoku.convert.RankDataConvert;
import com.sudoku.convert.UserGameInformationConvert;
import com.sudoku.log.BusinessType;
import com.sudoku.log.Log;
import com.sudoku.mapper.SudokuLevelMapper;
import com.sudoku.mapper.UserGameInformationMapper;
import com.sudoku.model.bo.GameRecordBO;
import com.sudoku.model.bo.RankItemBO;
import com.sudoku.model.entity.SudokuLevel;
import com.sudoku.model.entity.UserGameInformation;
import com.sudoku.model.vo.RankDataVO;
import com.sudoku.model.vo.UserGameInformationVO;
import com.sudoku.service.UserGameInformationService;
import com.sudoku.utils.CoreUtils;
import com.sudoku.utils.PublicUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户游戏信息业务层实现类
 */
@Service
public class UserGameInformationServiceImpl implements UserGameInformationService {

  @Autowired
  private HttpSession session;
  @Autowired
  private UserGameInformationMapper userGameInformationMapper;
  @Autowired
  private SudokuLevelMapper sudokuLevelMapper;

  /**
   * 更新用户游戏信息
   */
  @Transactional
  @Log(value = "更新用户游戏信息", businessType = BusinessType.UPDATE)
  @Override
  public void updateUserGameInformation() {
    GameRecordBO gameRecord = (GameRecordBO) session.getAttribute(GAME_RECORD_KEY);
    UserGameInformation information = userGameInformationMapper.selectByUidAndSlid(gameRecord.getUid(), gameRecord.getSlid());
    if (CoreUtils.isGameStart(gameRecord)) {
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
  @Transactional
  @Override
  public List<UserGameInformationVO> getUserGameInformation() {
    //获取当前用户的游戏信息
    Integer userId = CoreUtils.getNowUser().getId();
    List<UserGameInformation> userGameInformationList = userGameInformationMapper.selectByUid(userId);
    //获取数独级别列表
    List<SudokuLevel> sudokuLevels = sudokuLevelMapper.selectAll();
    //若当前用户的游戏信息比数独级别数少，则缺少了信息
    if (sudokuLevels.size() > userGameInformationList.size()) {
      insertUserLackSudokuLevelInformation(userGameInformationList, sudokuLevels, userId);
      userGameInformationList = userGameInformationMapper.selectByUid(userId);
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
  @Transactional
  @Log("初始化用户游戏信息")
  @Override
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
    Set<Integer> slids = userGameInformationList.stream().map(UserGameInformation::getSlid).collect(Collectors.toSet());
    List<Integer> lackSlids = new ArrayList<>(sudokuLevels.size() - userGameInformationList.size());
    sudokuLevels.forEach(sudokuLevel -> {
      Integer slid = sudokuLevel.getId();
      if (!slids.contains(slid)) {
        lackSlids.add(slid);
      }
    });
    userGameInformationMapper.batchInsertByUserIdAndSlids(userId, lackSlids);
  }

  /**
   * 将用户游戏信息持久层对象列表转换为显示层对象列表
   *
   * @param userGameInformationList 用户游戏信息持久层对象列表
   * @return 用户游戏信息显示层对象列表
   */
  private List<UserGameInformationVO> convertToUserGameInformationVOList(List<UserGameInformation> userGameInformationList) {
    List<UserGameInformationVO> list = new ArrayList<>();
    Map<Integer, String> idToName = sudokuLevelMapper.selectIdToName();
    userGameInformationList.forEach(userGameInformation -> {
      UserGameInformationVO convert = UserGameInformationConvert.INSTANCE.convert(userGameInformation);
      convert.setSudokuLevelName(idToName.get(userGameInformation.getSlid()));
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
    List<RankItemBO<Integer>> list = userGameInformationMapper
        .selectCorrectNumberRanking(SettingParameter.RANKING_NUMBER);
    return RankDataConvert.INSTANCE.convert(list, RankDataName.CORRECT_NUMBER);
  }

  /**
   * 获取以最少用时排名的列表
   *
   * @return 排行数据显示层
   */
  private RankDataVO<Integer> getMinSpendTimeRankingList() {
    List<RankItemBO<Integer>> list = userGameInformationMapper
        .selectMinSpendTimeRanking(SettingParameter.RANKING_NUMBER);
    return RankDataConvert.INSTANCE.convert(list, RankDataName.MIN_SPEND_TIME);
  }

  /**
   * 获取以平均用时排名的列表
   *
   * @return 排行数据显示层
   */
  private RankDataVO<Integer> getAverageSpendTimeRankingList() {
    List<RankItemBO<Integer>> list = userGameInformationMapper
        .selectAverageSpendTimeRanking(SettingParameter.RANKING_NUMBER);
    return RankDataConvert.INSTANCE.convert(list, RankDataName.AVERAGE_SPEND_TIME);
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

    userGameInformationMapper.updateByUidAndSlid(information, gameRecord.getUid(), gameRecord.getSlid());
  }

  /**
   * 增加用户游戏总数
   *
   * @param gameRecord 游戏记录
   * @param total      用户之前总数
   */
  private void plusUserGameTotal(GameRecordBO gameRecord, Integer total) {
    userGameInformationMapper.updateTotalByUidAndSlid(total + 1, gameRecord.getUid(), gameRecord.getSlid());
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
