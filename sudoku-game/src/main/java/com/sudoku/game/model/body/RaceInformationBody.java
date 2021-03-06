package com.sudoku.game.model.body;

import com.sudoku.common.core.domain.LocalDateTimeRange;
import com.sudoku.common.validator.IsLocalDateTimeRange;
import com.sudoku.game.validator.IsSudokuMatrix;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("竞赛内容信息体类")
public class RaceInformationBody implements Serializable {

  private static final long serialVersionUID = 9008509762495627768L;

  @ApiModelProperty("数独矩阵")
  @IsSudokuMatrix
  private List<List<Integer>> matrix;

  @ApiModelProperty("题目空缺数组")
  @IsSudokuMatrix
  private List<List<Boolean>> holes;

  @ApiModelProperty("竞赛的标题")
  @Length(min = 4, max = 64, message = "竞赛标题需在4-64个字符之间")
  private String title;

  @ApiModelProperty("竞赛的描述")
  @Length(max = 512, message = "竞赛描述不能超过512个字符")
  private String description;

  @ApiModelProperty("竞赛时间范围")
  @IsLocalDateTimeRange(startNotNull = true, endNotNull = true)
  private LocalDateTimeRange raceTimeRange;

  @ApiModelProperty("数独难度ID")
  private Integer sudokuLevelId;
}
