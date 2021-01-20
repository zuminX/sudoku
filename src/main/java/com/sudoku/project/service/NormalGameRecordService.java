package com.sudoku.project.service;

import com.sudoku.common.constant.enums.AnswerSituation;
import com.sudoku.common.tools.page.Page;
import com.sudoku.common.tools.page.PageParam;
import com.sudoku.common.tools.page.PageUtils;
import com.sudoku.common.utils.PublicUtils;
import com.sudoku.common.utils.SecurityUtils;
import com.sudoku.project.convert.NormalGameRecordConvert;
import com.sudoku.project.convert.SudokuRecordConvert;
import com.sudoku.project.mapper.NormalGameRecordMapper;
import com.sudoku.project.mapper.SudokuRecordMapper;
import com.sudoku.project.model.bo.SudokuRecordBO;
import com.sudoku.project.model.entity.NormalGameRecord;
import com.sudoku.project.model.entity.SudokuRecord;
import com.sudoku.project.model.result.NormalGameRecordResultForHistory;
import com.sudoku.project.model.vo.NormalGameRecordVO;
import com.sudoku.project.model.vo.UserGameInformationVO;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NormalGameRecordService {

  private final NormalGameRecordMapper normalGameRecordMapper;

  private final SudokuRecordMapper sudokuRecordMapper;

  private final SudokuRecordConvert sudokuRecordConvert;

  private final NormalGameRecordConvert normalGameRecordConvert;

  public NormalGameRecordService(NormalGameRecordMapper normalGameRecordMapper, SudokuRecordMapper sudokuRecordMapper,
      SudokuRecordConvert sudokuRecordConvert, NormalGameRecordConvert normalGameRecordConvert) {
    this.normalGameRecordMapper = normalGameRecordMapper;
    this.sudokuRecordMapper = sudokuRecordMapper;
    this.sudokuRecordConvert = sudokuRecordConvert;
    this.normalGameRecordConvert = normalGameRecordConvert;
  }

  @Transactional
  public void insertGameRecord(List<List<Integer>> inputMatrix, AnswerSituation answerSituation, SudokuRecordBO sudokuRecordBO) {
    SudokuRecord sudokuRecord = sudokuRecordConvert.convert(sudokuRecordBO);
    sudokuRecordMapper.insert(sudokuRecord);

    NormalGameRecord normalGameRecord = NormalGameRecord.builder()
        .inputMatrix(PublicUtils.compressionIntList(inputMatrix))
        .answerSituation(answerSituation.getCode())
        .userId(SecurityUtils.getCurrentUserId())
        .sudokuRecordId(sudokuRecord.getSudokuLevelId()).build();
    normalGameRecordMapper.insert(normalGameRecord);
  }

  /**
   * 获取当前用户的游戏信息
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
  public List<UserGameInformationVO> getUserGameInformation(Integer userId) {
    return normalGameRecordMapper.selectGameInformationByUserId(userId);
  }

  /**
   * 获取历史游戏记录
   *
   * @param page     当前查询页
   * @param pageSize 每页显示的条数
   * @return 游戏记录的分页信息
   */
  public Page<NormalGameRecordVO> getHistoryGameRecord(Integer page, Integer pageSize) {
    return getHistoryGameRecord(SecurityUtils.getCurrentUserId(), page, pageSize);
  }

  /**
   * 根据用户ID，获取其历史游戏记录
   *
   * @param userId   用户ID
   * @param page     当前查询页
   * @param pageSize 每页显示的条数
   * @return 游戏记录的分页信息
   */
  public Page<NormalGameRecordVO> getHistoryGameRecordById(Integer userId, Integer page, Integer pageSize) {
    return getHistoryGameRecord(userId, page, pageSize);
  }

  /**
   * 获取指定用户的历史游戏记录
   *
   * @param id       用户ID
   * @param page     当前查询页
   * @param pageSize 每页显示的条数
   * @return 游戏记录的分页信息
   */
  private Page<NormalGameRecordVO> getHistoryGameRecord(Integer id, Integer page, Integer pageSize) {
    return PageUtils.getPage(PageParam.<NormalGameRecordResultForHistory>builder()
            .queryFunc(() -> normalGameRecordMapper.findByUserIdOrderByStartTimeDesc(id))
            .page(page)
            .pageSize(pageSize)
            .build(),
        normalGameRecordConvert::convert);
  }

}
