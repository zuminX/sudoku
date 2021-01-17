package com.sudoku.framework.task;

import com.sudoku.common.log.BusinessType;
import com.sudoku.common.log.Log;
import com.sudoku.project.service.RaceInformationService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.stereotype.Component;

/**
 * 游戏竞赛定时任务类
 */
@Component
public class GameRaceTask {

  private final RaceInformationService raceInformationService;

  public GameRaceTask(RaceInformationService raceInformationService) {
    this.raceInformationService = raceInformationService;
  }

  /**
   * 移除过期的竞赛信息缓存
   *
   * @param s 参数
   * @return 执行结果
   */
  @XxlJob("removeCacheExpiredRaceInformationTask")
  @Log(value = "移除过期竞赛信息缓存", businessType = BusinessType.DELETE)
  public ReturnT<String> removeCacheExpiredRaceInformationTask(String s) {
    return ReturnT.SUCCESS;
  }
}
