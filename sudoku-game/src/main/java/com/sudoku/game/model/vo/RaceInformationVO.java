package com.sudoku.game.model.vo;

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
@ApiModel("竞赛信息显示层类")
public class RaceInformationVO implements Serializable {

  private static final long serialVersionUID = 7526373506092340922L;

  @ApiModelProperty("竞赛信息的ID")
  private Integer id;

  @ApiModelProperty("竞赛的标题")
  private String title;

  @ApiModelProperty("竞赛的描述")
  private String description;

  @ApiModelProperty("开始时间")
  private LocalDateTime startTime;

  @ApiModelProperty("结束时间")
  private LocalDateTime endTime;
}
