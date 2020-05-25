package com.sudoku.convert;

import com.sudoku.constant.consist.SettingParameter;
import com.sudoku.model.dto.RankItemDTO;
import com.sudoku.model.vo.RankDataVO;
import com.sudoku.model.vo.RankDataVO.RankItemVO;
import com.sudoku.utils.CoreUtils;
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
   * @param rankItemDTOList 排行项传输层列表
   * @param rankDataName    排行数据名称
   * @param <T>             数据的类型
   * @return 排行数据显示层对象
   */
  default <T> RankDataVO<T> convert(List<RankItemDTO<T>> rankItemDTOList, String rankDataName) {
    var rankItemMap = new LinkedHashMap<String, List<RankItemVO<T>>>();
    ArrayList<RankItemVO<T>> rankItemVOList = new ArrayList<>(SettingParameter.RANKING_NUMBER);
    for (int i = 0, size = rankItemDTOList.size(); i < size; i++) {
      RankItemDTO<T> rankItemDTO = rankItemDTOList.get(i);
      rankItemVOList.add(RankItemConvert.INSTANCE.convert(rankItemDTO));

      String levelName = rankItemDTO.getSudokuLevelName();
      //若为最后一项，将其添加到Map中
      if (i == size - 1) {
        rankItemMap.put(levelName, CoreUtils.fillUpRankItemList(rankItemVOList));
        //否则，若当前项的等级名称与下一项的等级名称不同，即将要进入新的等级
      } else if (!levelName.equals(rankItemDTOList.get(i + 1).getSudokuLevelName())) {
        //将其添加到Mao中，并初始化排行项列表
        rankItemMap.put(levelName, CoreUtils.fillUpRankItemList(rankItemVOList));
        rankItemVOList = new ArrayList<>(SettingParameter.RANKING_NUMBER);
      }
    }
    return new RankDataVO<>(rankDataName, rankItemMap);
  }
}
