package com.sudoku.common.constant.enums;

import com.sudoku.common.constant.consist.RedisKeys;
import com.sudoku.project.core.TransformRankItemToTypedTupleCallback;
import com.sudoku.project.core.TransformTypedTupleToRankItemCallback;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@ApiModel("排行类型类")
public enum RankingType {

  AVERAGE_SPEND_TIME("平均花费时间", RedisKeys.AVERAGE_SPEND_TIME_RANKING_PREFIX, TransformRankItemToTypedTupleCallback.ASCENDING_RANK,
      TransformTypedTupleToRankItemCallback.ASCENDING_RANK),
  CORRECT_NUMBER("回答正确数", RedisKeys.CORRECT_NUMBER_RANKING_PREFIX, TransformRankItemToTypedTupleCallback.DESCENDING_RANK,
      TransformTypedTupleToRankItemCallback.DESCENDING_RANK),
  MIN_SPEND_TIME("最小花费时间", RedisKeys.MIN_SPEND_TIME_RANKING_PREFIX, TransformRankItemToTypedTupleCallback.ASCENDING_RANK,
      TransformTypedTupleToRankItemCallback.ASCENDING_RANK);

  @ApiModelProperty("排行名称")
  private final String name;

  @ApiModelProperty("Redis的Key值前缀")
  private final String redisPrefix;

  @ApiModelProperty("排行项数据转换为值-分数对的回调方法")
  private final TransformRankItemToTypedTupleCallback rankItemToTypedTupleCallback;

  @ApiModelProperty("值-分数对转换为排行项数据的回调方法")
  private final TransformTypedTupleToRankItemCallback typedTupleToRankItemCallback;

  /**
   * 获取所有排行类型名
   *
   * @return 排行类型名列表
   */
  public static List<String> getAllRankingTypeName() {
    return Arrays.stream(RankingType.values()).map(RankingType::getName).collect(Collectors.toList());
  }

  /**
   * 根据名字查找对应的排行类型对象
   *
   * @param name 排行类型名
   * @return 若存在该名字的排行类型对象，则将其返回。否则返回null
   */
  public static RankingType findByName(String name) {
    return Arrays.stream(RankingType.values())
        .filter(statisticsDate -> statisticsDate.getName().equals(name))
        .findFirst()
        .orElse(null);
  }
}
