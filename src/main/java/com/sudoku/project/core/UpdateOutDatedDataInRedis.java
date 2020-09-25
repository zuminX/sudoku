package com.sudoku.project.core;

import cn.hutool.extra.spring.SpringUtil;
import com.sudoku.common.tools.RedisUtils;
import com.sudoku.project.model.bo.LocalDateStamped;
import java.time.LocalDate;

/**
 * 更新Redis中过期数据类
 */
public abstract class UpdateOutDatedDataInRedis<T extends LocalDateStamped> {

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
   * 更新在Redis中，当天之前更新的数据
   *
   * @return 更新后的数据
   */
  public T updateData() {
    T data = redisUtils.getObject(key);
    LocalDate nowDate = LocalDate.now();
    if (data == null) {
      data = getLatestDataIfEmpty();
    } else {
      LocalDate updateDate = data.getUpdateDate();
      if (nowDate.compareTo(updateDate) <= 0) {
        return data;
      }
      data = getLatestData(data);
    }
    data.setUpdateDate(nowDate);
    redisUtils.setObject(key, data);
    return data;
  }

  /**
   * 当Redis中无该Key的数据时，获取最新的数据
   *
   * @return 最新的数据
   */
  public abstract T getLatestDataIfEmpty();

  /**
   * 当Redis中有该Key的数据但其更新时间超过当前日期，获取最新的数据
   *
   * @return 最新的数据
   */
  public abstract T getLatestData(T oldData);
}
