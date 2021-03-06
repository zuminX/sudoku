package com.sudoku.system.model.body;

import com.sudoku.common.core.domain.LocalDateTimeRange;
import com.sudoku.common.validator.IsLocalDateTimeRange;
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
  @IsLocalDateTimeRange
  private LocalDateTimeRange createTimeRange;

  @ApiModelProperty("最近登录时间范围")
  @IsLocalDateTimeRange
  private LocalDateTimeRange recentLoginTimeRange;

  @ApiModelProperty("是否启用")
  private Boolean enabled;

  @ApiModelProperty("角色名列表")
  private List<String> roleNameList;
}
