package com.sudoku.project.core;

import cn.hutool.extra.spring.SpringUtil;
import com.sudoku.common.tools.DataStamped;
import com.sudoku.common.tools.RedisUtils;
import java.time.LocalDate;

/**
 * 更新Redis中过期数据的模板方法
 *
 * @param <T> 数据类型
 */
public class UpdateOutDatedDataInRedis<T> {

  private final String key;

  private final RedisUtils redisUtils;

  /**
   * 该类的构造方法
   *
   * @param key 数据存放在Redis中的Key
   */
  public UpdateOutDatedDataInRedis(String key) {
    this.key = key;
    this.redisUtils = SpringUtil.getBean(RedisUtils.class);
  }

  /**
   * 更新存储在Redis中，当天之前更新的数据
   *
   * @return 更新后的数据
   */
  public T updateData(GetLatestDataCallback<T> getLatestDataCallback, GetLatestDataIfEmptyCallback<T> getLatestDataIfEmptyCallback) {
    DataStamped<T> dataStamped = redisUtils.getObject(key);
    if (dataStamped == null) {
      T data = getLatestDataIfEmptyCallback.getLatestDataIfEmpty();
      redisUtils.setObject(key, new DataStamped<>(data));
      return data;
    }
    LocalDate nowDate = LocalDate.now();
    if (nowDate.compareTo(dataStamped.getUpdateDate()) <= 0) {
      return dataStamped.getData();
    }
    T latestData = getLatestDataCallback.getLatestData(dataStamped);
    dataStamped.setData(latestData);
    dataStamped.setUpdateDate(nowDate);
    redisUtils.setObject(key, dataStamped);
    return latestData;
  }

  /**
   * 当Redis中无该Key的数据时，获取最新的数据的回调方法
   *
   * @param <T> 数据类型
   */
  public interface GetLatestDataIfEmptyCallback<T> {

    /**
     * 当Redis中无该Key的数据时，获取最新的数据
     *
     * @return 最新的数据
     */
    T getLatestDataIfEmpty();
  }

  /**
   * 当Redis中有该Key的数据但其更新时间超过当前日期，获取最新的数据的回调方法
   *
   * @param <T> 数据类型
   */
  public interface GetLatestDataCallback<T> {

    /**
     * 当Redis中有该Key的数据但其更新时间超过当前日期，获取最新的数据
     *
     * @param oldData 旧数据
     * @return 最新的数据
     */
    T getLatestData(DataStamped<T> oldData);
  }
}
