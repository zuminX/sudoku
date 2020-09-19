package com.sudoku.project.convert;

import com.sudoku.common.constant.consist.SettingParameter;
import com.sudoku.project.model.bo.RankItemBO;
import com.sudoku.project.model.vo.RankDataVO;
import com.sudoku.project.model.vo.RankItemVO;
import com.sudoku.common.utils.CoreUtils;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 排行数据转换器
 */
@Mapper
public interface RankDataConvert {

  RankDataConvert INSTANCE = Mappers.getMapper(RankDataConvert.class);

  /**
   * 将排行项传输层列表转换为排行数据显示层对象
   *
   * @param rankItemBOList 排行项传输层列表
   * @param rankDataName   排行数据名称
   * @param <T>            数据的类型
   * @return 排行数据显示层对象
   */
  default <T> RankDataVO<T> convert(List<RankItemBO<T>> rankItemBOList, String rankDataName) {
    LinkedHashMap<String, List<RankItemVO<T>>> rankItemMap = new LinkedHashMap<>();
    ArrayList<RankItemVO<T>> rankItemVOList = new ArrayList<>(SettingParameter.RANKING_NUMBER);
    for (int i = 0, size = rankItemBOList.size(); i < size; i++) {
      RankItemBO<T> rankItemBO = rankItemBOList.get(i);
      rankItemVOList.add(RankItemConvert.INSTANCE.convert(rankItemBO));

      String levelName = rankItemBO.getSudokuLevelName();
      //若为最后一项，将其添加到Map中
      if (i == size - 1) {
        rankItemMap.put(levelName, CoreUtils.fillUpRankItemList(rankItemVOList));
        break;
      }
      //若当前项的等级名称与下一项的等级名称不同，即将要进入新的等级
      if (!levelName.equals(rankItemBOList.get(i + 1).getSudokuLevelName())) {
        //将其添加到Mao中，并初始化排行项列表
        rankItemMap.put(levelName, CoreUtils.fillUpRankItemList(rankItemVOList));
        rankItemVOList = new ArrayList<>(SettingParameter.RANKING_NUMBER);
      }
    }
    return new RankDataVO<>(rankDataName, rankItemMap);
  }
}
