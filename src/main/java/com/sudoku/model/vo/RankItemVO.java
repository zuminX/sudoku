package com.sudoku.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel("排行项显示类")
public class RankItemVO<T> implements Serializable {

  private static final long serialVersionUID = 5068815021945647289L;

  @ApiModelProperty("用户昵称")
  private String nickname;

  @ApiModelProperty("排行项数据")
  private T data;
}