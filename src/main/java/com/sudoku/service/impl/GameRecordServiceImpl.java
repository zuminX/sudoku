package com.sudoku.service.impl;

import static com.sudoku.constant.consist.SessionKey.GAME_RECORD_KEY;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sudoku.convert.GameRecordConvert;
import com.sudoku.convert.GameRecordPageConvert;
import com.sudoku.log.BusinessType;
import com.sudoku.log.Log;
import com.sudoku.mapper.GameRecordMapper;
import com.sudoku.model.bo.GameRecordBO;
import com.sudoku.model.entity.GameRecord;
import com.sudoku.model.vo.GameRecordPageVO;
import com.sudoku.model.vo.GameRecordVO;
import com.sudoku.service.GameRecordService;
import com.sudoku.utils.CoreUtils;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 游戏记录业务层实现类
 */
@Service
public class GameRecordServiceImpl implements GameRecordService {

  @Autowired
  private HttpSession session;
  @Autowired
  private GameRecordMapper gameRecordMapper;

  /**
   * 保存游戏记录
   */
  @Transactional
  @Log(value = "保存游戏记录", businessType = BusinessType.SAVE)
  @Override
  public void saveGameRecord() {
    GameRecordBO gameRecordBO = (GameRecordBO) session.getAttribute(GAME_RECORD_KEY);
    if (CoreUtils.isGameEnd(gameRecordBO)) {
      updateGameRecord(gameRecordBO);
    } else {
      insertGameRecord(gameRecordBO);
    }
  }

  private void insertGameRecord(GameRecordBO gameRecordBO) {
    GameRecord gameRecord = GameRecordConvert.INSTANCE.convert(gameRecordBO);
    gameRecordMapper.insertSelective(gameRecord);

    gameRecordBO.setId(gameRecord.getId());
    session.setAttribute(GAME_RECORD_KEY, gameRecordBO);
  }

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
  public GameRecordPageVO getHistoryGameRecord(Integer page, Integer pageSize) {
    PageHelper.startPage(page, pageSize);
    Integer uid = CoreUtils.getNowUser().getId();
    PageInfo<GameRecordVO> gameRecordPageInfo = new PageInfo<>(gameRecordMapper.findByUidOrderByStartTimeDesc(uid));
    return GameRecordPageConvert.INSTANCE.convert(gameRecordPageInfo);
  }
}
