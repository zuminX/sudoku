package com.sudoku.framework.task;

import com.sudoku.common.log.BusinessType;
import com.sudoku.common.log.Log;
import com.sudoku.project.service.GameRankService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 游戏排行定时任务类
 */
@Component
public class GameRankTask implements ApplicationRunner {

  private final GameRankService gameRankService;

  /**
   * 游戏排行定时任务类的构造方法
   *
   * @param gameRankService 游戏排行业务层对象
   */
  public GameRankTask(GameRankService gameRankService) {
    this.gameRankService = gameRankService;
  }

  /**
   * 初始化游戏排行任务
   *
   * @param s 参数
   * @return 执行结果
   */
  @XxlJob("initGameRankTask")
  @Log(value = "初始化游戏排行", businessType = BusinessType.INSERT)
  public ReturnT<String> initGameRankTask(String s) {
    gameRankService.initRanking();
    return ReturnT.SUCCESS;
  }

  /**
   * 删除排行榜中超过排行榜人数限制的数据任务
   *
   * @param s 参数
   * @return 执行结果
   */
  @XxlJob("removeExceedNumberDataTask")
  @Log(value = "删除排行榜中超过排行榜人数限制的数据", businessType = BusinessType.DELETE)
  public ReturnT<String> removeExceedNumberDataTask(String s) {
    gameRankService.removeExceedNumberData();
    return ReturnT.SUCCESS;
  }

  /**
   * 系统启动后初始化游戏排行
   *
   * @param args 应用参数
   */
  @Override
  public void run(ApplicationArguments args) {
    this.initGameRankTask("");
  }
}
