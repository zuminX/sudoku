package mock;

import com.sudoku.common.tools.RedisUtils;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;

public class RedisUtilsMock extends RedisUtils {

  private final HashMap<String, Object> data;

  public RedisUtilsMock() {
    super(null);
    data = new HashMap<>();
  }

  @Override
  public <T> void setObject(String key, T value) {
    data.put(key, value);
  }

  @Override
  public <T> void setObject(String key, T value, long timeout, TimeUnit timeUnit) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <T> T getObject(String key) {
    return (T) data.get(key);
  }

  @Override
  public void deleteObject(String key) {
    data.remove(key);
  }

  @Override
  public void deleteCollection(Collection collection) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <T> void setList(String key, List<T> dataList) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <T> List<T> getList(String key) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <T> void setSet(String key, Set<T> dataSet) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <T> Set<T> getSet(String key) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <T> void setZSet(String key, Set<TypedTuple<T>> typedTuples) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <T> void addZSet(String key, T value, double score) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <T> void addZSet(String key, TypedTuple<T> tuple) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <T> Set<T> getZSet(String key) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <T> Set<T> getZSetByRange(String key, long start, long end) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <T> Set<TypedTuple<T>> getZSetByRangeWithScores(String key, long start, long end) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <T> Long getZSetRank(String key, T value) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <T> Long getZSetSize(String key) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void removeZSetByRange(String key, long start, long end) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void removeZSetByScoreRange(String key, double min, double max) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <T> void setMap(String key, Map<String, T> dataMap) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <T> Map<String, T> getMap(String key) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Collection<String> keys(String pattern) {
    throw new UnsupportedOperationException();
  }
}
