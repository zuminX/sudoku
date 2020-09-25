package com.sudoku.common.utils;

import static com.sudoku.common.constant.consist.RedisKeys.GAME_RECORD_PREFIX;

import com.sudoku.common.tools.RedisUtils;
import com.sudoku.project.model.bo.GameRecordBO;
import java.util.concurrent.TimeUnit;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 游戏工具类
 */
@Component
public class GameUtils {

  /**
   * 每局数独游戏最长存在时间(h)
   */
  private static final int GAME_MAX_TIME = 2;

  @Autowired
  private RedisUtils redisUtils;

  /**
   * 判断游戏是否结束
   *
   * @param gameRecord 游戏记录传输层对象
   * @return 游戏结束返回true，否则返回false
   */
  public static boolean isGameEnd(@NotNull GameRecordBO gameRecord) {
    return gameRecord.getEndTime() != null;
  }

  /**
   * 判断游戏是否开始
   *
   * @param gameRecord 游戏记录传输层对象
   * @return 游戏开始返回true，否则返回false
   */
  public static boolean isGameStart(@NotNull GameRecordBO gameRecord) {
    return !isGameEnd(gameRecord);
  }

  /**
   * 从redis中获取当前游戏记录
   *
   * @return 游戏记录
   */
  public GameRecordBO getGameRecord() {
    return redisUtils.getObject(getGameRecordKey());
  }

  /**
   * 保存游戏记录至redis中
   *
   * @param gameRecord 游戏记录
   */
  public void setGameRecord(GameRecordBO gameRecord) {
    redisUtils.setObject(getGameRecordKey(), gameRecord, GAME_MAX_TIME, TimeUnit.HOURS);
  }

  /**
   * 获取游戏记录的key值
   *
   * @return 游戏记录在redis中的key值
   */
  private String getGameRecordKey() {
    return GAME_RECORD_PREFIX + SecurityUtils.getUserId();
  }
}
