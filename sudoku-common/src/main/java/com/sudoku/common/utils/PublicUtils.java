package com.sudoku.common.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * 公共工具类
 */
public class PublicUtils {

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
    if (CollUtil.isEmpty(list)) {
      return new int[0][0];
    }

    final int row = list.size();
    final int[][] result = new int[row][];
    for (int i = 0; i < row; i++) {
      List<Integer> rowList = list.get(i);
      final int column = CollUtil.isEmpty(rowList) ? 0 : rowList.size();
      result[i] = new int[column];
      for (int j = 0; j < column; j++) {
        Integer element = rowList.get(j);
        result[i][j] = element == null ? 0 : element;
      }
    }
    return result;
  }

  public static Integer[][] wrap(int[][] list) {
    return Arrays.stream(list).map(ArrayUtil::wrap).toArray(Integer[][]::new);
  }


  public static Boolean[][] wrap(boolean[][] list) {
    return Arrays.stream(list).map(ArrayUtil::wrap).toArray(Boolean[][]::new);
  }

  /**
   * 将二维Boolean型List转为二维boolean数组
   *
   * @param list 列表
   * @return 转换后的二维数组
   */
  public static boolean[][] unwrapBoolArray(List<List<Boolean>> list) {
    if (CollUtil.isEmpty(list)) {
      return new boolean[0][0];
    }

    final int row = list.size();
    final boolean[][] result = new boolean[row][];
    for (int i = 0; i < row; i++) {
      List<Boolean> rowList = list.get(i);
      final int column = CollUtil.isEmpty(rowList) ? 0 : rowList.size();
      result[i] = new boolean[column];
      for (int j = 0; j < column; j++) {
        Boolean element = rowList.get(j);
        result[i][j] = element != null && element;
      }
    }
    return result;
  }

  /**
   * 获取[min,max]的随机整数
   *
   * @param min 最小值
   * @param max 最大值
   * @return [min, max]之间的一个整数
   */
  public static int getRandomInt(int min, int max) {
    if (min > max) {
      throw new IllegalArgumentException("最小值必须小于或等于最大值");
    }
    return RANDOM.nextInt(max - min + 1) + min;
  }

  /**
   * 深拷贝二维int型数组
   *
   * @param source 源数据
   * @return 深拷贝的数组
   */
  public static int[][] deepClone(int[][] source) {
    if (source == null) {
      return null;
    }
    int[][] clone = new int[source.length][];
    for (int i = 0; i < source.length; i++) {
      clone[i] = new int[source[i].length];
      System.arraycopy(source[i], 0, clone[i], 0, source[i].length);
    }
    return clone;
  }

  /**
   * 深拷贝二维boolean型数组
   *
   * @param source 源数据
   * @return 深拷贝的数组
   */
  public static boolean[][] deepClone(boolean[][] source) {
    if (source == null) {
      return null;
    }
    boolean[][] clone = new boolean[source.length][];
    for (int i = 0; i < source.length; i++) {
      clone[i] = new boolean[source[i].length];
      System.arraycopy(source[i], 0, clone[i], 0, source[i].length);
    }
    return clone;
  }

  /**
   * 利用Knuth洗牌算法打乱一维数组
   *
   * @param array 待打乱的数组
   */
  public static void randomizedArray(int[] array) {
    for (int i = array.length - 1; i > 0; i--) {
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
    for (int row = array.length, column = array[0].length, i = row * column - 1; i > 0; i--) {
      int random = getRandomInt(0, i);
      boolean temp = array[i / row][i % column];
      array[i / row][i % column] = array[random / row][random % column];
      array[random / row][random % column] = temp;
    }
  }

  /**
   * 压缩二维int型数组为字符串
   *
   * @param array 二维int型数组
   * @return 对应的字符串
   */
  public static String compressionIntArray(int[][] array) {
    if (ArrayUtil.isEmpty(array)) {
      return "";
    }
    return Arrays.stream(array).flatMapToInt(Arrays::stream).mapToObj(String::valueOf).collect(Collectors.joining());
  }

  /**
   * 压缩二维int型列表为字符串
   *
   * @param list 二维int型列表
   * @return 对应的字符串
   */
  public static String compressionIntList(List<List<Integer>> list) {
    return PublicUtils.compressionIntArray(PublicUtils.unwrapIntArray(list));
  }

  /**
   * 压缩二维boolean型数组为字符串
   *
   * @param array 二维boolean型数组
   * @return 对应的字符串
   */
  public static String compressionBoolArray(boolean[][] array) {
    if (ArrayUtil.isEmpty(array)) {
      return "";
    }
    StringBuilder sb = new StringBuilder();
    Arrays.stream(array).forEach(booleans -> {
      for (boolean aBoolean : booleans) {
        sb.append(aBoolean ? 1 : 0);
      }
    });
    return sb.toString();
  }

  /**
   * 压缩二维boolean型列表为字符串
   *
   * @param list 二维boolean型列表
   * @return 对应的字符串
   */
  public static String compressionBoolList(List<List<Boolean>> list) {
    return PublicUtils.compressionBoolArray(PublicUtils.unwrapBoolArray(list));
  }

}
