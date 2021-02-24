package com.sudoku.common.tools;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("时间日期的范围类")
public class DateTimeRange implements Serializable {

  private static final long serialVersionUID = -2310827848773144726L;

  @ApiModelProperty("开始的日期时间")
  private LocalDateTime start;

  @ApiModelProperty("结束的日期时间")
  private LocalDateTime end;
}
