package com.sudoku.project.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel("排行数据显示类")
public class RankDataVO<T> implements Serializable {

  private static final long serialVersionUID = 5967517292862185830L;

  @ApiModelProperty("排行名称")
  private String rankName;

  @ApiModelProperty("排行项映射 键为难度名，值为排行项集合")
  private Map<String, List<RankItemVO<T>>> rankItemMap;

}
