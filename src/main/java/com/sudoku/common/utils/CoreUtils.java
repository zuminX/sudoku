package com.sudoku.common.utils;

import com.sudoku.common.constant.consist.SettingParameter;
import com.sudoku.project.model.vo.RankItemVO;
import java.util.List;
import java.util.stream.IntStream;

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
    IntStream.range(rankItemVOArrayList.size(), SettingParameter.RANKING_NUMBER).<RankItemVO<T>>mapToObj(
        i -> new RankItemVO<>(null, null)).forEach(rankItemVOArrayList::add);
    return rankItemVOArrayList;
  }

}
