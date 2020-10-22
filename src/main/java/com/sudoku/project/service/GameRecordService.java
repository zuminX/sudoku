package com.sudoku.project.service;

import com.sudoku.common.tools.page.Page;
import com.sudoku.project.model.vo.GameRecordVO;

/**
 * 游戏记录业务层接口
 */
public interface GameRecordService {

  /**
   * 保存游戏记录
   */
  void saveGameRecord();

  /**
   * 获取历史游戏记录
   *
   * @param page     当前查询页
   * @param pageSize 每页显示的条数
   * @return 游戏记录的分页信息
   */
  Page<GameRecordVO> getHistoryGameRecord(Integer page, Integer pageSize);

  /**
   * 根据用户ID，获取其历史游戏记录
   *
   * @param userId       用户ID
   * @param page     当前查询页
   * @param pageSize 每页显示的条数
   * @return 游戏记录的分页信息
   */
  Page<GameRecordVO> getHistoryGameRecordById(Integer userId, Integer page, Integer pageSize);
}
