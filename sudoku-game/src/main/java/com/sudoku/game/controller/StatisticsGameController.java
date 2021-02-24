package com.sudoku.game.controller;

import com.sudoku.common.constant.enums.StatisticsDate;
import com.sudoku.common.constant.enums.StatusCode;
import com.sudoku.common.exception.StatisticsException;
import com.sudoku.game.service.StatisticsGameService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
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

  @GetMapping("/game/assignTotal")
  @PreAuthorize("@ss.hasPermission('statistics:game:total')")
  @ApiOperation("获取指定日期的游戏局数")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "startDate", value = "开始日期", dataTypeClass = LocalDateTime.class, required = true),
      @ApiImplicitParam(name = "endDate", value = "结束日期", dataTypeClass = LocalDateTime.class, required = true),
      @ApiImplicitParam(name = "date", value = "统计日期类", dataTypeClass = StatisticsDate.class, required = true)
  })
  public List<Integer> getAssignDateGameTotal(
      @RequestParam @NotNull(message = "开始日期不能为空") @Past(message = "开始日期必须是过去的时间") LocalDate startDate,
      @RequestParam @NotNull(message = "结束日期不能为空") LocalDate endDate,
      @RequestParam StatisticsDate date) {
    if (startDate.compareTo(endDate) > 0) {
      throw new StatisticsException(StatusCode.STATISTICS_INQUIRY_DATE_INVALID);
    }
    return statisticsGameService.getGameTotal(startDate, endDate, date);
  }

  @GetMapping("/game/recentTotal")
  @PreAuthorize("@ss.hasPermission('statistics:game:total')")
    @ApiOperation("获取最近日期的游戏局数")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "date", value = "统计日期类", dataTypeClass = StatisticsDate.class, required = true)
  })
  public List<Integer> getRecentDateGameTotal(@RequestParam StatisticsDate date) {
    LocalDate endDate = LocalDate.now();
    LocalDate startDate = date.minus(endDate, 7L);
    return getAssignDateGameTotal(startDate, endDate, date);
  }

  @GetMapping("/game/total")
  @PreAuthorize("@ss.hasPermission('statistics:game:total')")
  @ApiOperation("获取系统游戏总局数")
  public Integer getGameTotal() {
    return statisticsGameService.getGameTotal();
  }
}
