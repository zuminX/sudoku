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
import java.util.Date;
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
    //根据结束时间判断游戏状态
    Date endTime = gameRecordBO.getEndTime();
    //开始游戏
    if (endTime == null) {
      //转换为对应表对象，并插入到数据库中
      GameRecord gameRecord = GameRecordConvert.INSTANCE.convert(gameRecordBO);
      gameRecordMapper.insertSelective(gameRecord);
      //设置回填的ID，并保存到session中
      gameRecordBO.setId(gameRecord.getId());
      session.setAttribute(GAME_RECORD_KEY, gameRecordBO);
      //游戏结束
    } else {
      gameRecordMapper.updateEndTimeAndCorrectById(endTime, gameRecordBO.getCorrect(), gameRecordBO.getId());
    }
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
