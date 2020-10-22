package com.sudoku.project.model.bo;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 排行项业务类
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RankItemBO implements Serializable {

  @Serial
  private static final long serialVersionUID = 4710978968972411799L;

  /**
   * 数独等级ID
   */
  private Integer sudokuLevelId;

  /**
   * 排行项数据列表
   */
  private List<RankItemDataBO> rankItemDataList;
}
