package com.sudoku.framework.task;

import com.sudoku.common.log.BusinessType;
import com.sudoku.common.log.Log;
import com.sudoku.project.service.GameRankService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.stereotype.Component;

/**
 * 游戏排行的定时任务
 */
@Component
public class GameRankTask {

  private final GameRankService gameRankService;

  public GameRankTask(GameRankService gameRankService) {
    this.gameRankService = gameRankService;
  }

  @XxlJob("initGameRankTask")
  @Log(value = "初始化游戏排行", businessType = BusinessType.INSERT)
  public ReturnT<String> initGameRankTask(String s) {
    gameRankService.initRanking();
    return ReturnT.SUCCESS;
  }

}
