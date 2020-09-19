package com.sudoku.project.controller;

import com.sudoku.common.constant.enums.StatisticsDateName;
import com.sudoku.common.exception.FormParameterConversionException;
import com.sudoku.framework.security.service.StatisticsGameService;
import com.sudoku.framework.security.service.StatisticsUserService;
import com.sudoku.project.model.bo.StatisticsUserDataBO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.beans.PropertyEditorSupport;
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

  @GetMapping("/user")
  @PreAuthorize("@ss.hasPermission('statistics:user:list')")
  @ApiOperation("获取用户统计数据")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "startDate", value = "开始日期", dataTypeClass = LocalDateTime.class, required = true),
      @ApiImplicitParam(name = "endDate", value = "结束日期", dataTypeClass = LocalDateTime.class, required = true),
      @ApiImplicitParam(name = "dateName", value = "统计日期的名字", dataTypeClass = StatisticsDateName.class, required = true)
  })
  public List<StatisticsUserDataBO> getStatisticsUserData(
      @RequestParam @NotNull(message = "开始日期不能为空") @Past(message = "开始日期必须是过去的时间") LocalDateTime startDate,
      @RequestParam LocalDateTime endDate,
      @RequestParam StatisticsDateName dateName) {
    return statisticsUserService.getStatisticsUserData(startDate, endDate, dateName);
  }

  /**
   * 将前台传递过来的统计日期的字符串，自动转化为对应的枚举类型
   */
  @InitBinder
  protected void initBinder(WebDataBinder binder) {
    // Date 类型转换
    binder.registerCustomEditor(StatisticsDateName.class, new PropertyEditorSupport() {
      @Override
      public void setAsText(String text) {
        StatisticsDateName dateName = StatisticsDateName.findByName(text);
        if (dateName == null) {
          throw new FormParameterConversionException(text, StatisticsDateName.class);
        }
        setValue(dateName);
      }
    });
  }

}
