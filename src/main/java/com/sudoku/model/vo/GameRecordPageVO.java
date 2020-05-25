package com.sudoku.model.vo;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 游戏记录分页数据显示层
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class GameRecordPageVO implements Serializable {

  private static final long serialVersionUID = 7901612911640882955L;
  /**
   * 总页数
   */
  private Integer totalPage;
  /**
   * 当前页
   */
  private Integer currentPage;
  /**
   * 每页的数量
   */
  private Integer pageSize;
  /**
   * 游戏记录
   */
  private List<GameRecordVO> gameRecord;
}
