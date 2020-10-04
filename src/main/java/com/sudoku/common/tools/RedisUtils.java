package com.sudoku.common.tools;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

/**
 * Redis工具类
 */
@Component
@SuppressWarnings(value = {"unchecked", "rawtypes"})
public class RedisUtils {

  @Autowired
  public RedisTemplate redisTemplate;

  /**
   * 缓存对象
   *
   * @param key   缓存的键值
   * @param value 缓存的值
   */
  public <T> void setObject(String key, T value) {
    if (isKeyValueNull(key, value)) {
      return;
    }
    redisTemplate.opsForValue().set(key, value);
  }

  /**
   * 缓存对象，并设置其的过期时间
   *
   * @param key      缓存的键值
   * @param value    缓存的值
   * @param timeout  时间
   * @param timeUnit 时间颗粒度
   */
  public <T> void setObject(String key, T value, long timeout, TimeUnit timeUnit) {
    if (isKeyValueNull(key, value) || timeUnit == null) {
      return;
    }
    redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
  }

  /**
   * 获得缓存对象
   *
   * @param key 缓存键值
   * @return 缓存键值对应的数据
   */
  public <T> T getObject(String key) {
    if (isKeyNull(key)) {
      return null;
    }
    ValueOperations<String, T> operation = redisTemplate.opsForValue();
    return operation.get(key);
  }

  /**
   * 删除对象
   *
   * @param key 缓存键值
   */
  public void deleteObject(String key) {
    if (isKeyNull(key)) {
      return;
    }
    redisTemplate.delete(key);
  }

  /**
   * 删除集合
   *
   * @param collection 集合对象
   */
  public void deleteCollection(Collection collection) {
    redisTemplate.delete(collection);
  }

  /**
   * 缓存列表数据
   *
   * @param key      缓存的键值
   * @param dataList 待缓存的List数据
   */
  public <T> void setList(String key, List<T> dataList) {
    if (isKeyCollectionNull(key, dataList)) {
      return;
    }
    redisTemplate.opsForList().leftPushAll(key, dataList);
  }

  /**
   * 获得缓存的列表对象
   *
   * @param key 缓存的键值
   * @return 缓存键值对应的数据
   */
  public <T> List<T> getList(String key) {
    if (isKeyNull(key)) {
      return ListUtil.empty();
    }

    ListOperations<String, T> listOperation = redisTemplate.opsForList();
    Long size = listOperation.size(key);
    if (size == null) {
      return ListUtil.empty();
    }

    return LongStream.range(0, size).mapToObj(i -> listOperation.index(key, i)).collect(Collectors.toList());
  }

  /**
   * 缓存集合数据
   *
   * @param key     缓存键值
   * @param dataSet 缓存的数据
   */
  public <T> void setSet(String key, Set<T> dataSet) {
    if (isKeyCollectionNull(key, dataSet)) {
      return;
    }
    BoundSetOperations<String, T> setOperation = redisTemplate.boundSetOps(key);
    for (T t : dataSet) {
      setOperation.add(t);
    }
  }

  /**
   * 获得缓存的集合数据
   *
   * @param key 缓存键值
   * @return 缓存键值对应的数据
   */
  public <T> Set<T> getSet(String key) {
    if (isKeyNull(key)) {
      return CollectionUtil.empty(Set.class);
    }
    BoundSetOperations<String, T> operation = redisTemplate.boundSetOps(key);
    return operation.members();
  }

  /**
   * 缓存Map数据
   *
   * @param key     缓存键值
   * @param dataMap 缓存的数据
   */
  public <T> void setMap(String key, Map<String, T> dataMap) {
    if (isKeyNull(key) || dataMap == null) {
      return;
    }
    HashOperations hashOperations = redisTemplate.opsForHash();
    dataMap.forEach((k, v) -> hashOperations.put(key, k, v));
  }

  /**
   * 获得缓存的Map数据
   *
   * @param key 缓存键值
   * @return 缓存键值对应的数据
   */
  public <T> Map<String, T> getMap(String key) {
    if (isKeyNull(key)) {
      return MapUtil.empty();
    }
    return (Map<String, T>) redisTemplate.opsForHash().entries(key);
  }

  /**
   * 获得缓存的基本对象列表
   *
   * @param pattern 字符串前缀
   * @return 对象列表
   */
  public Collection<String> keys(String pattern) {
    return redisTemplate.keys(pattern);
  }

  /**
   * 判断键值是否为空
   *
   * @param key 键值
   * @return 键值为空返回true，否则返回false
   */
  private boolean isKeyNull(String key) {
    return StrUtil.isBlank(key);
  }

  /**
   * 判断键值和数据是否为空
   *
   * @param key   键值
   * @param value 数据
   * @param <T>   数据类型
   * @return 有一为空返回true，否则返回false
   */
  private <T> boolean isKeyValueNull(String key, T value) {
    return isKeyNull(key) || value == null;
  }

  /**
   * 判断键值和集合数据是否为空
   *
   * @param key        键值
   * @param collection 集合数据
   * @return 有一为空返回true，否则返回false
   */
  private boolean isKeyCollectionNull(String key, Collection collection) {
    return isKeyNull(key) || CollectionUtil.isEmpty(collection);
  }
}