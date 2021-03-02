package com.sudoku.game.service;

import com.sudoku.common.constant.enums.AnswerSituation;
import com.sudoku.common.tools.page.Page;
import com.sudoku.common.tools.page.PageParam;
import com.sudoku.common.tools.page.PageUtils;
import com.sudoku.common.utils.PublicUtils;
import com.sudoku.game.convert.NormalGameRecordConvert;
import com.sudoku.game.mapper.NormalGameRecordMapper;
import com.sudoku.game.model.entity.NormalGameRecord;
import com.sudoku.game.model.result.NormalGameRecordResultForHistory;
import com.sudoku.game.model.vo.NormalGameRecordVO;
import com.sudoku.game.model.vo.UserGameInformationVO;
import com.sudoku.game.utils.sudoku.GameUtils;
import com.sudoku.system.utils.SecurityUtils;
import java.util.List;
import java.util.function.Supplier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 普通游戏记录的业务层类
 */
@Service
public class NormalGameRecordService {

  private final NormalGameRecordMapper normalGameRecordMapper;

  private final NormalGameRecordConvert normalGameRecordConvert;

  private final GameUtils gameUtils;

  public NormalGameRecordService(NormalGameRecordMapper normalGameRecordMapper, NormalGameRecordConvert normalGameRecordConvert,
      GameUtils gameUtils) {
    this.normalGameRecordMapper = normalGameRecordMapper;
    this.normalGameRecordConvert = normalGameRecordConvert;
    this.gameUtils = gameUtils;
  }

  /**
   * 更新游戏记录
   *
   * @param inputMatrix     用户输入的矩阵
   * @param answerSituation 回答情况
   */
  @Transactional
  public void updateGameRecord(List<List<Integer>> inputMatrix, AnswerSituation answerSituation) {
    normalGameRecordMapper.updateInputMatrixAndAnswerSituationBySudokuRecordId(
        PublicUtils.compressionIntList(inputMatrix),
        answerSituation.getCode(),
        gameUtils.getSudokuRecord().getId());
  }

  /**
   * 插入当前游戏的游戏记录
   */
  @Transactional
  public void insertNowGameRecord() {
    NormalGameRecord normalGameRecord = NormalGameRecord.builder()
        .userId(SecurityUtils.getCurrentUserId())
        .sudokuRecordId(gameUtils.getSudokuRecord().getSudokuLevelId()).build();
    normalGameRecordMapper.insert(normalGameRecord);
  }

  /**
   * 获取当前用户的游戏信息
   *
   * @return 用户游戏信息列表
   */
  public List<UserGameInformationVO> getUserGameInformation() {
    return getUserGameInformation(SecurityUtils.getCurrentUserId());
  }

  /**
   * 根据用户ID，获取其游戏信息
   *
   * @param userId 用户ID
   * @return 用户游戏信息列表
   */
  public List<UserGameInformationVO> getUserGameInformation(Integer userId) {
    return normalGameRecordMapper.selectGameInformationByUserId(userId);
  }

  /**
   * 获取历史游戏记录
   *
   * @return 游戏记录的分页信息
   */
  public Page<NormalGameRecordVO> getHistoryGameRecord() {
    Integer userId = SecurityUtils.getCurrentUserId();
    if (!gameUtils.isRecord()) {
      return getHistoryGameRecordById(userId);
    }
    //防止显示当前正在进行的游戏
    Integer nowSudokuRecordId = gameUtils.getSudokuRecord().getId();
    return getHistoryGameRecord(() -> normalGameRecordMapper.findByUserIdOrderByStartTimeDescIgnoreOneSudokuRecord(userId, nowSudokuRecordId));
  }

  /**
   * 根据用户ID，获取其历史游戏记录
   *
   * @param userId   用户ID
   * @return 普通游戏记录的分页信息
   */
  public Page<NormalGameRecordVO> getHistoryGameRecordById(Integer userId) {
    return getHistoryGameRecord(() -> normalGameRecordMapper.findByUserIdOrderByStartTimeDesc(userId));
  }

  /**
   * 获取历史游戏记录
   *
   * @param supplier 查询函数
   * @return 普通游戏记录的分页信息
   */
  private Page<NormalGameRecordVO> getHistoryGameRecord(Supplier<List<NormalGameRecordResultForHistory>> supplier) {
    return PageUtils.getPage(new PageParam<>(supplier), normalGameRecordConvert::convert);
  }

}
