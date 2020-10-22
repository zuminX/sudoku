package com.sudoku.project.service.impl;

import com.sudoku.common.constant.consist.SettingParameter;
import com.sudoku.common.constant.enums.RankingType;
import com.sudoku.common.constant.enums.StatusCode;
import com.sudoku.common.exception.SudokuLevelException;
import com.sudoku.common.tools.RedisUtils;
import com.sudoku.common.tools.page.Page;
import com.sudoku.common.tools.page.Page.PageInformation;
import com.sudoku.common.utils.SecurityUtils;
import com.sudoku.common.utils.project.SudokuLevelUtils;
import com.sudoku.project.core.GetRankingDataCallback;
import com.sudoku.project.core.TransformRankItemToTypedTupleCallback;
import com.sudoku.project.core.TransformTypedTupleToRankItemCallback;
import com.sudoku.project.mapper.UserGameInformationMapper;
import com.sudoku.project.model.bo.RankItemBO;
import com.sudoku.project.model.bo.RankItemDataBO;
import com.sudoku.project.model.entity.User;
import com.sudoku.project.model.entity.UserGameInformation;
import com.sudoku.project.service.GameRankService;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Service;

/**
 * 游戏排行业务层实现类
 */
@Service
public class GameRankServiceImpl implements GameRankService {

  private final UserGameInformationMapper userGameInformationMapper;
  private final RedisUtils redisUtils;
  private final SudokuLevelUtils sudokuLevelUtils;

  public GameRankServiceImpl(UserGameInformationMapper userGameInformationMapper, RedisUtils redisUtils,
      SudokuLevelUtils sudokuLevelUtils) {
    this.userGameInformationMapper = userGameInformationMapper;
    this.redisUtils = redisUtils;
    this.sudokuLevelUtils = sudokuLevelUtils;
  }

  /**
   * 初始化排行数据
   */
  @Override
  public void initRanking() {
    initRankingData(RankingType.AVERAGE_SPEND_TIME, userGameInformationMapper::selectAverageSpendTimeRanking);
    initRankingData(RankingType.MIN_SPEND_TIME, userGameInformationMapper::selectMinSpendTimeRanking);
    initRankingData(RankingType.CORRECT_NUMBER, userGameInformationMapper::selectCorrectNumberRanking);
  }

  /**
   * 获取排行数据
   *
   * @param rankingType     排行类型
   * @param sudokuLevelName 数独等级名称
   * @param page            当前查询页
   * @param pageSize        每页显示的条数
   * @return 排行项数据列表的分页信息
   */
  @Override
  public Page<RankItemDataBO> getRanking(RankingType rankingType, String sudokuLevelName, Integer page, Integer pageSize) {
    String rankingKey = getRankingKey(rankingType, sudokuLevelName);
    List<RankItemDataBO> rankItemDataList = getRankingData(rankingKey, rankingType.getTypedTupleToRankItemCallback(), page, pageSize);
    return transformToPage(page, pageSize, rankingKey, rankItemDataList);
  }

  /**
   * 获取当前用户指定排行的排名
   *
   * @param rankingType 排行类型
   * @return 若排名不存在或大于RANKING_NUMBER，则返回null；否则返回对应的排名
   */
  @Override
  public Long getCurrentUserRank(RankingType rankingType, String sudokuLevelName) {
    User user = SecurityUtils.getCurrentUser();
    String rankingKey = getRankingKey(rankingType, sudokuLevelName);
    Long rank = redisUtils.getZSetRank(rankingKey, user.getUsername());
    return rank == null || rank > SettingParameter.RANKING_NUMBER ? null : rank;
  }

  /**
   * 根据用户游戏信息更新对应的排名
   *
   * @param userGameInformation 用户游戏信息对象
   */
  @Override
  public void updateCurrentUserRank(UserGameInformation userGameInformation) {
    String username = SecurityUtils.getCurrentUser().getUsername();
    Integer sudokuLevelId = userGameInformation.getSudokuLevelId();
    updateUserRank(RankingType.AVERAGE_SPEND_TIME, sudokuLevelId, username, userGameInformation.getAverageSpendTime());
    updateUserRank(RankingType.MIN_SPEND_TIME, sudokuLevelId, username, userGameInformation.getMinSpendTime());
    updateUserRank(RankingType.CORRECT_NUMBER, sudokuLevelId, username, userGameInformation.getCorrectNumber());
  }

  /**
   * 更新用户的排名
   *
   * @param rankingType   排行类型
   * @param sudokuLevelId 数独等级ID
   * @param username      用户名
   * @param data          数据
   */
  private void updateUserRank(RankingType rankingType, Integer sudokuLevelId, String username, Integer data) {
    if (data == null) {
      return;
    }
    String key = getRankingKey(rankingType.getRedisPrefix(), sudokuLevelId);
    TypedTuple<String> tuple = rankingType.getRankItemToTypedTupleCallback().transform(new RankItemDataBO(username, data));
    redisUtils.addZSet(key, tuple);
  }

  /**
   * 获取排行的Redis的key
   *
   * @param rankingPrefix key值的前缀
   * @param sudokuLevelId 数独等级ID
   * @return 对应排行的key
   */
  private String getRankingKey(String rankingPrefix, Integer sudokuLevelId) {
    return rankingPrefix + sudokuLevelId;
  }

  /**
   * 获取排行的Redis的key
   *
   * @param rankingType     排行类型
   * @param sudokuLevelName 数独等级名称
   * @return 对应排行的key
   */
  private String getRankingKey(RankingType rankingType, String sudokuLevelName) {
    Integer sudokuLevelId = sudokuLevelUtils.getSudokuLevelIdByName(sudokuLevelName)
        .orElseThrow(SudokuLevelException.supplier(StatusCode.SUDOKU_LEVEL_NOT_FOUND));
    return getRankingKey(rankingType.getRedisPrefix(), sudokuLevelId);
  }

  /**
   * 初始化排行数据
   *
   * @param rankingType 排行类型
   * @param callback    获取排行项数据的回调方法
   */
  private void initRankingData(RankingType rankingType, GetRankingDataCallback callback) {
    List<RankItemBO> list = callback.getData(SettingParameter.RANKING_NUMBER);
    InsertRankItem insertRankItem = new InsertRankItem(rankingType);
    insertRankItem.insert(list);
  }

  /**
   * 获取下标为[start,end]的排行数据
   *
   * @param rankingKey 排行key值
   * @param callback   将排行项数据转换为值-分数对的回调方法
   * @param page       当前查询页
   * @param pageSize   每页显示的条数
   * @return 排行项数据列表
   */
  private List<RankItemDataBO> getRankingData(String rankingKey, TransformTypedTupleToRankItemCallback callback, Integer page,
      Integer pageSize) {
    long start = (page - 1) * pageSize, end = start + pageSize - 1;
    Set<TypedTuple<String>> rangeWithScores = redisUtils.getZSetByRangeWithScores(rankingKey, start, end);
    return rangeWithScores.stream().map(callback::transform).collect(Collectors.toList());
  }

  /**
   * 转化排行数据为分页数据
   *
   * @param page       当前查询页
   * @param pageSize   每页显示的条数
   * @param rankingKey 排行key值
   * @param data       排行数据列表
   * @return 排行项数据列表的分页信息
   */
  private Page<RankItemDataBO> transformToPage(Integer page, Integer pageSize, String rankingKey, List<RankItemDataBO> data) {
    int numberOfRanks = Math.toIntExact(redisUtils.getZSetSize(rankingKey));
    int totalPage = numberOfRanks / pageSize;
    if (numberOfRanks % pageSize != 0) {
      ++totalPage;
    }
    PageInformation pageInformation = new PageInformation(totalPage, page, pageSize);
    return new Page<>(pageInformation, data);
  }

  /**
   * 向Redis插入排行项数据的模板方法
   */
  @AllArgsConstructor
  private class InsertRankItem {

    private final String rankingPrefix;
    private final TransformRankItemToTypedTupleCallback callback;

    /**
     * 用排行类型进行构造
     *
     * @param rankingType 排行类型
     */
    public InsertRankItem(RankingType rankingType) {
      this.rankingPrefix = rankingType.getRedisPrefix();
      this.callback = rankingType.getRankItemToTypedTupleCallback();
    }

    /**
     * 向Redis中插入排行项数据
     *
     * @param rankItemList 排行项列表
     */
    public void insert(List<RankItemBO> rankItemList) {
      rankItemList.forEach(rankItem -> {
        List<RankItemDataBO> rankItemDataList = rankItem.getRankItemDataList();
        Set<TypedTuple<String>> tuples = rankItemDataList.stream().map(callback::transform).collect(Collectors.toSet());
        redisUtils.setZSet(getRankingKey(rankingPrefix, rankItem.getSudokuLevelId()), tuples);
      });
    }
  }
}
