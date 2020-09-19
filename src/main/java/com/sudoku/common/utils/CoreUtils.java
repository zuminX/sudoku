package com.sudoku.common.utils;

import com.sudoku.common.constant.consist.SettingParameter;
import com.sudoku.project.model.vo.RankItemVO;
import java.util.List;

/**
 * 业务相关工具类
 */
public class CoreUtils {

  /**
   * 私有构造方法，防止实例化
   */
  private CoreUtils() {
  }

  /**
   * 填充排行项列表到设定的数量
   *
   * @param <T> 数据的类型
   * @return 填充后的排行项列表
   */
  public static <T> List<RankItemVO<T>> fillUpRankItemList(List<RankItemVO<T>> rankItemVOArrayList) {
    for (int i = rankItemVOArrayList.size(); i < SettingParameter.RANKING_NUMBER; i++) {
      rankItemVOArrayList.add(new RankItemVO<>(null, null));
    }
    return rankItemVOArrayList;
  }

}
