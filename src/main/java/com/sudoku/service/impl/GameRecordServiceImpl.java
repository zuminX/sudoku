package com.sudoku.service.impl;

import com.sudoku.convert.GameRecordConvert;
import com.sudoku.log.BusinessType;
import com.sudoku.log.Log;
import com.sudoku.mapper.GameRecordMapper;
import com.sudoku.model.bo.GameRecordBO;
import com.sudoku.model.entity.GameRecord;
import com.sudoku.model.vo.GameRecordVO;
import com.sudoku.model.vo.PageVO;
import com.sudoku.service.GameRecordService;
import com.sudoku.utils.GameUtils;
import com.sudoku.utils.PageUtils;
import com.sudoku.utils.PageUtils.PageParam;
import com.sudoku.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 游戏记录业务层实现类
 */
@Service
public class GameRecordServiceImpl implements GameRecordService {

  @Autowired
  private GameRecordMapper gameRecordMapper;
  @Autowired
  private GameUtils gameUtils;

  /**
   * 保存游戏记录
   */
  @Override
  @Transactional
  @Log(value = "保存游戏记录", businessType = BusinessType.SAVE)
  public void saveGameRecord() {
    GameRecordBO gameRecordBO = gameUtils.getGameRecord();
    if (GameUtils.isGameEnd(gameRecordBO)) {
      updateGameRecord(gameRecordBO);
    } else {
      insertGameRecord(gameRecordBO);
    }
  }

  /**
   * 插入游戏记录
   *
   * @param gameRecordBO 游戏记录
   */
  private void insertGameRecord(GameRecordBO gameRecordBO) {
    GameRecord gameRecord = GameRecordConvert.INSTANCE.convert(gameRecordBO);
    gameRecordMapper.insertSelective(gameRecord);

    gameRecordBO.setId(gameRecord.getId());
    gameUtils.setGameRecord(gameRecordBO);
  }

  /**
   * 更新游戏记录
   *
   * @param gameRecordBO 游戏记录
   */
  private void updateGameRecord(GameRecordBO gameRecordBO) {
    gameRecordMapper.updateEndTimeAndCorrectById(gameRecordBO.getEndTime(), gameRecordBO.getCorrect(), gameRecordBO.getId());
  }

  /**
   * 获取历史游戏记录
   *
   * @param page     当前查询页
   * @param pageSize 每页显示的条数
   * @return 游戏记录的分页信息
   */
  @Override
  public PageVO<GameRecordVO> getHistoryGameRecord(Integer page, Integer pageSize) {
    return PageUtils.getPageVO(PageParam.<GameRecordVO>builder()
        .queryFunc(() -> gameRecordMapper.findByUidOrderByStartTimeDesc(SecurityUtils.getUserId()))
        .page(page)
        .pageSize(pageSize)
        .build());
  }
}
