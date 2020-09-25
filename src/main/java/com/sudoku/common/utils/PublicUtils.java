package com.sudoku.common.utils;

import static java.lang.reflect.Array.newInstance;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * 业务无关工具类
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
  public static int[][] unwrap(List<List<Integer>> list) {
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
   * 获取两个日期的绝对差值
   *
   * @param date1 日期一
   * @param date2 日期二
   * @return 以ms为单位的差值
   */
  public static long getTwoDateAbsDiff(Date date1, Date date2) {
    return Math.abs(date1.getTime() - date2.getTime());
  }


  /**
   * 压缩int型矩阵为字符串
   *
   * @param matrix int型矩阵
   * @return 对应的字符串
   */
  public static String compressionMatrix(int[][] matrix) {
    return Arrays.stream(matrix).flatMapToInt(Arrays::stream).mapToObj(String::valueOf).collect(Collectors.joining());
  }

  /**
   * 压缩boolean型矩阵为字符串
   *
   * @param matrix boolean型矩阵
   * @return 对应的字符串
   */
  public static String compressionMatrix(boolean[][] matrix) {
    StringBuilder sb = new StringBuilder();
    for (boolean[] booleans : matrix) {
      for (boolean aBoolean : booleans) {
        sb.append(aBoolean ? 1 : 0);
      }
    }
    return sb.toString();
  }

}
