package com.sudoku.project.model.body;

import com.sudoku.common.tools.DateTimeRange;
import com.sudoku.common.validator.IsDateTimeRange;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("搜索用户信息体类")
public class SearchUserBody implements Serializable {

  private static final long serialVersionUID = -231977380846579412L;

  @ApiModelProperty("用户名")
  private String username;

  @ApiModelProperty("昵称")
  private String nickname;

  @ApiModelProperty("创建时间范围")
  @IsDateTimeRange
  private DateTimeRange createTimeRange;

  @ApiModelProperty("最近登录时间范围")
  @IsDateTimeRange
  private DateTimeRange recentLoginTimeRange;

  @ApiModelProperty("是否启用")
  private Boolean enabled;

  @ApiModelProperty("角色名列表")
  private List<String> roleNameList;

  @ApiModelProperty("当前查询页")
  private Integer page;

  @Range(min = 1, max = 20, message = "每页显示的用户数在1-20个之间")
  @ApiModelProperty("每页显示的条数")
  private Integer pageSize;
}
