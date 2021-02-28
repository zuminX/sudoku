package com.sudoku.game.controller;

import com.sudoku.common.constant.enums.StatisticsDate;
import com.sudoku.common.core.domain.LocalDateRange;
import com.sudoku.common.core.domain.StatisticsDateRange;
import com.sudoku.game.service.StatisticsGameService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.time.LocalDate;
import java.util.List;
import javax.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statistics")
@Validated
@Api(tags = "统计信息API接口")
public class StatisticsGameController extends GameBaseController {

  private final StatisticsGameService statisticsGameService;

  public StatisticsGameController(StatisticsGameService statisticsGameService) {
    this.statisticsGameService = statisticsGameService;
  }

  @PostMapping("/game/assignTotal")
  @PreAuthorize("@ss.hasPermission('statistics:game:total')")
  @ApiOperation("获取指定日期的游戏局数")
  @ApiImplicitParam(name = "dateRange", value = "统计日期范围", dataTypeClass = StatisticsDateRange.class, required = true)
  public List<Integer> getAssignDateGameTotal(@RequestBody @Valid StatisticsDateRange dateRange) {
    return statisticsGameService.getGameTotal(dateRange);
  }

  @GetMapping("/game/recentTotal")
  @PreAuthorize("@ss.hasPermission('statistics:game:total')")
  @ApiOperation("获取最近日期的游戏局数")
  @ApiImplicitParam(name = "date", value = "统计日期类", dataTypeClass = StatisticsDate.class, required = true)
  public List<Integer> getRecentDateGameTotal(@RequestParam StatisticsDate date) {
    LocalDate endDate = LocalDate.now();
    LocalDate startDate = date.minus(endDate, 7L);
    return getAssignDateGameTotal(new StatisticsDateRange(new LocalDateRange(startDate, endDate), date));
  }

  @GetMapping("/game/total")
  @PreAuthorize("@ss.hasPermission('statistics:game:total')")
  @ApiOperation("获取系统游戏总局数")
  public Integer getGameTotal() {
    return statisticsGameService.getGameTotal();
  }
}
