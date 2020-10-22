package com.sudoku.common.constant.enums;

import com.sudoku.common.constant.consist.RedisKeys;
import com.sudoku.project.core.TransformRankItemToTypedTupleCallback;
import com.sudoku.project.core.TransformTypedTupleToRankItemCallback;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 排行类型
 */
@AllArgsConstructor
@Getter
public enum RankingType {

  AVERAGE_SPEND_TIME("平均花费时间", RedisKeys.AVERAGE_SPEND_TIME_RANKING_PREFIX, TransformRankItemToTypedTupleCallback.ASCENDING_RANK,
      TransformTypedTupleToRankItemCallback.ASCENDING_RANK),
  MIN_SPEND_TIME("最小花费时间", RedisKeys.MIN_SPEND_TIME_RANKING_PREFIX, TransformRankItemToTypedTupleCallback.ASCENDING_RANK,
      TransformTypedTupleToRankItemCallback.ASCENDING_RANK),
  CORRECT_NUMBER("回答正确数", RedisKeys.CORRECT_NUMBER_RANKING_PREFIX, TransformRankItemToTypedTupleCallback.DESCENDING_RANK,
      TransformTypedTupleToRankItemCallback.DESCENDING_RANK);

  private final String name;
  private final String redisPrefix;
  private final TransformRankItemToTypedTupleCallback rankItemToTypedTupleCallback;
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
