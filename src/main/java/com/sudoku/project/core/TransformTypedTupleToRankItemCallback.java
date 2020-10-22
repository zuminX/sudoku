package com.sudoku.project.core;

import com.sudoku.project.model.bo.RankItemDataBO;
import java.util.Objects;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;

/**
 * 将值-分数对转换为排行项数据的回调方法
 */
public interface TransformTypedTupleToRankItemCallback {

  /**
   * 处理按升序进行排行的数据
   * <p>
   * 由于升序排行时未对分数进行处理，因此转化后返回即可
   */
  TransformTypedTupleToRankItemCallback ASCENDING_RANK = typedTuple -> new RankItemDataBO(typedTuple.getValue(),
      Objects.requireNonNull(typedTuple.getScore()).intValue());

  /**
   * 将数据按降序进行排行
   * <p>
   * Redis中ZSet默认是升序的，因此需要对分数进行取反以达到降序排行的目的
   */
  TransformTypedTupleToRankItemCallback DESCENDING_RANK = typedTuple -> new RankItemDataBO(typedTuple.getValue(),
      -1 * Objects.requireNonNull(typedTuple.getScore()).intValue());

  /**
   * 将值-分数对转换为排行项数据
   *
   * @param typedTuple 值-分数对
   * @return 排行项数据
   */
  RankItemDataBO transform(TypedTuple<String> typedTuple);
}
