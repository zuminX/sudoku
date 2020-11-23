package com.sudoku.project.model.body;

import com.sudoku.common.tools.DateTimeRange;
import com.sudoku.common.validator.IsDateTimeRange;
import com.sudoku.common.validator.IsSudokuMatrix;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@NoArgsConstructor
@AllArgsConstructor
@Data
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
  @IsDateTimeRange(startCanNull = false, endTimeCanNull = false)
  private DateTimeRange raceTimeRange;
}
