package com.sudoku.common.utils;

import static java.lang.reflect.Array.newInstance;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * 公共工具类
 */
public class PublicUtils {

  /**
   * +8的时区
   */
  public static final ZoneOffset ZONE = ZoneOffset.of("+8");
  /**
   * 生成随机数的对象
   */
  private static final Random RANDOM = new Random();

  /**
   * 私有构造方法，防止实例化
   */
  private PublicUtils() {
  }

  /**
   * 将二维Integer型List转为二维int数组
   *
   * @param list 列表
   * @return 转换后的二维数组
   */
  public static int[][] unwrapIntArray(List<List<Integer>> list) {
    int[][] empty = new int[0][0];
    if (list == null) {
      return empty;
    }
    final int row = list.size();
    if (row == 0) {
      return empty;
    }
    final int column = list.get(0).size();
    if (column == 0) {
      return empty;
    }
    for (int i = 1; i < row; i++) {
      if (list.get(i).size() != column) {
        return empty;
      }
    }

    final int[][] result = new int[row][column];
    for (int i = 0; i < row; i++) {
      for (int j = 0; j < column; j++) {
        Integer element = list.get(i).get(j);
        result[i][j] = element == null ? 0 : element;
      }
    }
    return result;
  }

  /**
   * 将二维Boolean型List转为二维boolean数组
   *
   * @param list 列表
   * @return 转换后的二维数组
   */
  public static boolean[][] unwrapBoolArray(List<List<Boolean>> list) {
    boolean[][] empty = new boolean[0][0];
    if (list == null) {
      return empty;
    }
    final int row = list.size();
    if (row == 0) {
      return empty;
    }
    final int column = list.get(0).size();
    if (column == 0) {
      return empty;
    }
    for (int i = 1; i < row; i++) {
      if (list.get(i).size() != column) {
        return empty;
      }
    }

    final boolean[][] result = new boolean[row][column];
    for (int i = 0; i < row; i++) {
      for (int j = 0; j < column; j++) {
        Boolean element = list.get(i).get(j);
        result[i][j] = element != null && element;
      }
    }
    return result;
  }

  /**
   * 获取[begin,end]的随机整数
   *
   * @param begin 最小值
   * @param end   最大值
   * @return [begin, end]之间的一个整数
   */
  public static int getRandomInt(int begin, int end) {
    return RANDOM.nextInt(end - begin + 1) + begin;
  }

  /**
   * 深拷贝一个二维数组
   *
   * @param <T>  泛型
   * @param src  源数据
   * @param type 一维数组的class对象
   * @return 深拷贝的二维数组
   */
  @SuppressWarnings("unchecked")
  public static <T> T[] clone(T[] src, Class<T> type) {
    if (src == null) {
      return null;
    }
    T[] array = (T[]) newInstance(type, src.length);
    System.arraycopy(src, 0, array, 0, array.length);
    return array;
  }

  /**
   * 利用Knuth洗牌算法打乱一维数组
   *
   * @param array 待打乱的数组
   */
  public static void randomizedArray(int[] array) {
    for (int i = array.length - 1; i >= 0; i--) {
      int random = getRandomInt(0, i);
      int temp = array[i];
      array[i] = array[random];
      array[random] = temp;
    }
  }

  /**
   * 利用Knuth洗牌算法打乱二维boolean型数组
   *
   * @param array 待打乱的数组
   */
  public static void randomizedArray(boolean[][] array) {
    for (int row = array.length, column = array[0].length, i = row * column - 1; i >= 0; i--) {
      int random = getRandomInt(0, i);
      boolean temp = array[i / row][i % column];
      array[i / row][i % column] = array[random / row][random % column];
      array[random / row][random % column] = temp;
    }
  }

  /**
   * 获取两个日期时间的差值
   *
   * @param dateTime1 日期时间一
   * @param dateTime2 日期时间二
   * @return 以ms为单位的差值
   */
  public static long computeDiff(LocalDateTime dateTime1, LocalDateTime dateTime2) {
    return toTimestamp(dateTime1) - toTimestamp(dateTime2);
  }

  /**
   * 获取两个日期时间的绝对差值
   *
   * @param dateTime1 日期时间一
   * @param dateTime2 日期时间二
   * @return 以ms为单位的差值
   */
  public static long computeAbsDiff(LocalDateTime dateTime1, LocalDateTime dateTime2) {
    return Math.abs(computeDiff(dateTime1, dateTime2));
  }

  /**
   * 将本地日期时间转换时间戳
   *
   * @param localDateTime 本地日期时间
   * @return 对应的时间戳
   */
  public static long toTimestamp(LocalDateTime localDateTime) {
    return localDateTime.toInstant(ZONE).toEpochMilli();
  }

  /**
   * 压缩int型数组为字符串
   *
   * @param array int型数组
   * @return 对应的字符串
   */
  public static String compressionIntArray(int[][] array) {
    return Arrays.stream(array).flatMapToInt(Arrays::stream).mapToObj(String::valueOf).collect(Collectors.joining());
  }

  /**
   * 压缩int型列表为字符串
   *
   * @param list int型列表
   * @return 对应的字符串
   */
  public static String compressionIntList(List<List<Integer>> list) {
    return PublicUtils.compressionIntArray(PublicUtils.unwrapIntArray(list));
  }

  /**
   * 压缩boolean型数组为字符串
   *
   * @param array boolean型数组
   * @return 对应的字符串
   */
  public static String compressionBoolArray(boolean[][] array) {
    StringBuilder sb = new StringBuilder();
    Arrays.stream(array).forEach(booleans -> {
      for (boolean aBoolean : booleans) {
        sb.append(aBoolean ? 1 : 0);
      }
    });
    return sb.toString();
  }

  /**
   * 压缩boolean型列表为字符串
   *
   * @param list boolean型列表
   * @return 对应的字符串
   */
  public static String compressionBoolList(List<List<Boolean>> list) {
    return PublicUtils.compressionBoolArray(PublicUtils.unwrapBoolArray(list));
  }
}
