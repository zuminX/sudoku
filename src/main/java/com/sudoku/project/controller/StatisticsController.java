package com.sudoku.project.controller;

import com.sudoku.common.constant.enums.StatisticsDate;
import com.sudoku.common.exception.FormParameterConversionException;
import com.sudoku.project.model.bo.StatisticsUserDataBO;
import com.sudoku.project.service.StatisticsGameService;
import com.sudoku.project.service.StatisticsUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statistics")
@Validated
@Api(tags = "统计信息API接口")
public class StatisticsController {

  @Autowired
  private StatisticsUserService statisticsUserService;
  @Autowired
  private StatisticsGameService statisticsGameService;

  @GetMapping("/user/assignDate")
  @PreAuthorize("@ss.hasPermission('statistics:user:list')")
  @ApiOperation("获取指定日期的用户统计数据")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "startDate", value = "开始日期", dataTypeClass = LocalDateTime.class, required = true),
      @ApiImplicitParam(name = "endDate", value = "结束日期", dataTypeClass = LocalDateTime.class, required = true),
      @ApiImplicitParam(name = "date", value = "统计日期类", dataTypeClass = StatisticsDate.class, required = true)
  })
  public List<StatisticsUserDataBO> getAssignDateUserData(
      @RequestParam @NotNull(message = "开始日期不能为空") @Past(message = "开始日期必须是过去的时间") LocalDate startDate,
      @RequestParam @NotNull(message = "结束日期不能为空") LocalDate endDate,
      @RequestParam StatisticsDate date) {
    return statisticsUserService.getStatisticsUserData(startDate, endDate, date);
  }

  @GetMapping("/user/recentDate")
  @PreAuthorize("@ss.hasPermission('statistics:user:list')")
  @ApiOperation("获取最近日期的用户统计数据")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "date", value = "统计日期类", dataTypeClass = StatisticsDate.class, required = true)
  })
  public List<StatisticsUserDataBO> getRecentDateUserData(@RequestParam StatisticsDate date) {
    LocalDate endDate = LocalDate.now();
    LocalDate startDate = date.minus(endDate, 7L);
    return getAssignDateUserData(startDate, endDate, date);
  }

  @GetMapping("/user/total")
  @PreAuthorize("@ss.hasPermission('statistics:user:total')")
  @ApiOperation("获取系统的用户总数")
  public Integer getUserTotal() {
    return statisticsUserService.getUserTotal();
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
    return statisticsGameService.getGameTotal(startDate, endDate, date);
  }

  @GetMapping("/game/recentTotal")
  @PreAuthorize("@ss.hasPermission('statistics:game:total')")
    @ApiOperation("获取最近日期的游戏局数")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "date", value = "统计日期类", dataTypeClass = StatisticsDate.class, required = true)
  })
  public List<Integer> getRecentDateStatisticsUserData(@RequestParam StatisticsDate date) {
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

  /**
   * 将前台传递过来的统计日期的字符串，自动转化为对应的枚举类型
   */
  @InitBinder
  protected void initBinder(WebDataBinder binder) {
    binder.registerCustomEditor(StatisticsDate.class, new PropertyEditorSupport() {
      @Override
      public void setAsText(String text) {
        StatisticsDate dateName = StatisticsDate.findByName(text);
        if (dateName == null) {
          throw new FormParameterConversionException(text, StatisticsDate.class);
        }
        setValue(dateName);
      }
    });
  }

}
