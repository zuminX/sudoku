package com.sudoku.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel("分页数据显示层类")
public class PageVO<T> implements Serializable {

  private static final long serialVersionUID = 4427124527401721636L;

  @ApiModelProperty("分页信息")
  private PageInformation pageInformation;

  @ApiModelProperty("分页数据")
  private List<T> list;

  @NoArgsConstructor
  @AllArgsConstructor
  @Data
  @ApiModel("分页信息类")
  public static class PageInformation implements Serializable {

    private static final long serialVersionUID = 8500765176094961217L;

    @ApiModelProperty("总页数")
    private Integer totalPage;

    @ApiModelProperty("当前页")
    private Integer currentPage;

    @ApiModelProperty("每页的数量")
    private Integer pageSize;
  }
}
