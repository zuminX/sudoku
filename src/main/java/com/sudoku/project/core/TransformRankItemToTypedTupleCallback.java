package com.sudoku.project.core;

import com.sudoku.project.model.bo.RankItemDataBO;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;

/**
 * 将排行项数据转换为值-分数对的回调方法
 */
public interface TransformRankItemToTypedTupleCallback {

  /**
   * 将数据按升序进行排行
   * <p>
   * Redis中ZSet默认是升序的，因此不需要对分数进行改动
   */
  TransformRankItemToTypedTupleCallback ASCENDING_RANK = rankItemData -> new DefaultTypedTuple<>(rankItemData.getUsername(),
      rankItemData.getData().doubleValue());

  /**
   * 将数据按降序进行排行
   * <p>
   * Redis中ZSet默认是升序的，因此需要对分数进行取反以达到降序排行的目的
   */
  TransformRankItemToTypedTupleCallback DESCENDING_RANK = rankItemData -> new DefaultTypedTuple<>(rankItemData.getUsername(),
      -1 * rankItemData.getData().doubleValue());

  /**
   * 将排行项数据转换为值-分数对
   *
   * @param rankItemData 排行项数据
   * @return 值-分数对
   */
  TypedTuple<String> transform(RankItemDataBO rankItemData);

}
