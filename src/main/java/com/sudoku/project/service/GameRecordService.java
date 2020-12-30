package com.sudoku.project.service;

import com.sudoku.common.log.BusinessType;
import com.sudoku.common.log.Log;
import com.sudoku.common.tools.page.Page;
import com.sudoku.common.tools.page.PageParam;
import com.sudoku.common.tools.page.PageUtils;
import com.sudoku.common.utils.SecurityUtils;
import com.sudoku.common.utils.sudoku.GameUtils;
import com.sudoku.project.convert.GameRecordConvert;
import com.sudoku.project.mapper.GameRecordMapper;
import com.sudoku.project.model.bo.GameRecordBO;
import com.sudoku.project.model.vo.GameRecordVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 游戏记录业务层类
 */
@Service
public class GameRecordService {

  private final GameRecordMapper gameRecordMapper;

  private final GameRecordConvert gameRecordConvert;

  public GameRecordService(GameRecordMapper gameRecordMapper, GameRecordConvert gameRecordConvert) {
    this.gameRecordMapper = gameRecordMapper;
    this.gameRecordConvert = gameRecordConvert;
  }

  @Transactional
  @Log(value = "插入游戏记录", businessType = BusinessType.INSERT)
  public void insertGameRecord(GameRecordBO gameRecordBO) {
    gameRecordMapper.insert(gameRecordConvert.convert(gameRecordBO));
  }

  /**
   * 获取历史游戏记录
   *
   * @param page     当前查询页
   * @param pageSize 每页显示的条数
   * @return 游戏记录的分页信息
   */
  public Page<GameRecordVO> getHistoryGameRecord(Integer page, Integer pageSize) {
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
  public Page<GameRecordVO> getHistoryGameRecordById(Integer userId, Integer page, Integer pageSize) {
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
  private Page<GameRecordVO> getHistoryGameRecord(Integer id, Integer page, Integer pageSize) {
    return PageUtils.getPage(PageParam.<GameRecordVO>builder()
        .queryFunc(() -> gameRecordMapper.findByUserIdAndCorrectNotNullOrderByStartTimeDesc(id))
        .page(page)
        .pageSize(pageSize)
        .build());
  }
}
