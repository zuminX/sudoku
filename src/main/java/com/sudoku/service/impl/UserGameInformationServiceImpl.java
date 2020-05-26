package com.sudoku.service.impl;

import static com.sudoku.constant.consist.SessionKey.GAME_RECORD_KEY;

import com.sudoku.constant.consist.RankDataName;
import com.sudoku.constant.consist.SettingParameter;
import com.sudoku.convert.RankDataConvert;
import com.sudoku.convert.UserGameInformationConvert;
import com.sudoku.mapper.SudokuLevelMapper;
import com.sudoku.mapper.UserGameInformationMapper;
import com.sudoku.model.dto.GameRecordDTO;
import com.sudoku.model.dto.RankItemDTO;
import com.sudoku.model.po.SudokuLevel;
import com.sudoku.model.po.UserGameInformation;
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
  @Override
  public void updateUserGameInformation() {
    //获取session中的游戏记录
    GameRecordDTO gameRecord = (GameRecordDTO) session.getAttribute(GAME_RECORD_KEY);
    Integer uid = gameRecord.getUid();
    Integer slid = gameRecord.getSlid();

    UserGameInformation information = userGameInformationMapper.selectByUidAndSlid(uid, slid);
    Integer total = information.getTotal();

    //游戏开始
    if (gameRecord.getEndTime() == null) {
      userGameInformationMapper.updateTotalByUidAndSlid(total + 1, uid, slid);
      //游戏结束且答题正确
    } else if (gameRecord.getCorrect()) {
      int diff = Math.toIntExact(PublicUtils.getTwoDateAbsDiff(gameRecord.getEndTime(), gameRecord.getStartTime()));
      Integer correctNumber = information.getCorrectNumber();

      if (information.getAverageSpendTime() == null) {
        information.setAverageSpendTime(diff);
      } else {
        long totalAverageSpendTime = (long) information.getAverageSpendTime() * correctNumber;
        information.setAverageSpendTime(Math.toIntExact(((totalAverageSpendTime + diff) / (correctNumber + 1))));
      }
      if (information.getMinSpendTime() == null || information.getMinSpendTime() > diff) {
        information.setMinSpendTime(diff);
      }
      if (information.getMaxSpendTime() == null || information.getMaxSpendTime() < diff) {
        information.setMaxSpendTime(diff);
      }
      information.setCorrectNumber(correctNumber + 1);

      userGameInformationMapper.updateByUidAndSlid(information, uid, slid);
    }
  }

  /**
   * 获取用户游戏信息
   *
   * @return 用户游戏信息的显示层列表
   */
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
  @Cacheable(cacheNames = "rankList")
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
   * 获取以回答正确个数排行的列表
   *
   * @return 排行数据显示层
   */
  private RankDataVO<Integer> getCorrectNumberRankingList() {
    List<RankItemDTO<Integer>> list = userGameInformationMapper
        .selectLimitNumberGroupBySlidOrderByCorrectNumber(SettingParameter.RANKING_NUMBER);
    return RankDataConvert.INSTANCE.convert(list, RankDataName.CORRECT_NUMBER);
  }

  /**
   * 获取以最少用时排行的列表
   *
   * @return 排行数据显示层
   */
  private RankDataVO<Integer> getMinSpendTimeRankingList() {
    List<RankItemDTO<Integer>> list = userGameInformationMapper.selectLimitNumberGroupBySlidOrderByMinSpendTime(SettingParameter.RANKING_NUMBER);
    return RankDataConvert.INSTANCE.convert(list, RankDataName.MIN_SPEND_TIME);
  }

  /**
   * 获取以平均用时排行的列表
   *
   * @return 排行数据显示层
   */
  private RankDataVO<Integer> getAverageSpendTimeRankingList() {
    List<RankItemDTO<Integer>> list = userGameInformationMapper.selectLimitNumberGroupBySlidOrderByAverageSpendTime(SettingParameter.RANKING_NUMBER);
    return RankDataConvert.INSTANCE.convert(list, RankDataName.AVERAGE_SPEND_TIME);
  }
}
