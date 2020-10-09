package com.sudoku.common.tools;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel("时间日期的范围类")
public class DateTimeRange {

  @ApiModelProperty("开始的日期时间")
  private LocalDateTime start;
  @ApiModelProperty("结束的日期时间")
  private LocalDateTime end;
}
