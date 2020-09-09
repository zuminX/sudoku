package com.sudoku.service;

import com.sudoku.model.vo.GameRecordVO;
import com.sudoku.model.vo.PageVO;

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
  PageVO<GameRecordVO> getHistoryGameRecord(Integer page, Integer pageSize);
}
