package com.sudoku.common.core.domain;

import com.sudoku.common.constant.enums.StatisticsDate;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("统计日期范围")
public class StatisticsDateRange {

  @ApiModelProperty(value = "开始日期", required = true)
  @NotNull(message = "开始日期不能为空")
  @Past(message = "开始日期必须是过去的时间")
  private LocalDate startDate;

  @ApiModelProperty(value = "结束日期", required = true)
  @NotNull(message = "结束日期不能为空")
  private LocalDate endDate;

  @ApiModelProperty(value = "统计日期", required = true)
  @NotNull(message = "统计日期不能为空")
  private StatisticsDate statisticsDate;
}
