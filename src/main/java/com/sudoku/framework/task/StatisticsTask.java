package com.sudoku.framework.task;

import com.sudoku.common.log.BusinessType;
import com.sudoku.common.log.Log;
import com.sudoku.project.service.StatisticsUserService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 统计信息的定时任务
 */
@Component
public class StatisticsTask {

  @Autowired
  private StatisticsUserService statisticsUserService;

  @XxlJob("statisticsUserDailyTask")
  @Log(value = "统计每日用户数据", businessType = BusinessType.INSERT)
  public ReturnT<String> statisticsUserDailyTask(String s) {
    statisticsUserService.updateDailyDataUntilNow();
    return ReturnT.SUCCESS;
  }

  @XxlJob("statisticsUserEachMonthTask")
  @Log(value = "统计每月用户数据", businessType = BusinessType.INSERT)
  public ReturnT<String> statisticsUserEachMonthTask(String s) {
    statisticsUserService.updateEachMonthDataUntilNow();
    return ReturnT.SUCCESS;
  }
}
