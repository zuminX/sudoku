package com.sudoku.project.core;

import com.sudoku.project.model.bo.RankItemBO;
import java.util.List;

/**
 * 获取排行项数据的回调方法
 */
public interface GetRankingDataCallback {

  /**
   * 获取排行项数据
   *
   * @param limit 限制数
   * @return 排行项数据列表
   */
  List<RankItemBO> getData(Integer limit);
}
