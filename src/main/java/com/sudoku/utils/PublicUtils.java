package com.sudoku.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * 业务无关工具类
 */
public class PublicUtils {

  /**
   * 生成随机数的对象
   */
  private static final Random RANDOM;

  static {
    RANDOM = new Random();
  }

  public static int[][] unwrap(ArrayList<ArrayList<Integer>> list) {
    if (list == null) {
      return null;
    }
    final int row = list.size();
    if (row == 0) {
      return null;
    }
    final int column = list.get(0).size();
    if (column == 0) {
      return null;
    }
    for (int i = 1; i < row; i++) {
      if (list.get(i).size() != column) {
        return null;
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
   * 深拷贝二维int型数组
   *
   * @param src 源数据
   * @return 深拷贝的二维int型数组
   */
  public static int[][] getClone(int[][] src) {
    int[][] result = new int[src.length][src[0].length];
    for (int i = 0, length = src.length; i < length; i++) {
      System.arraycopy(src[i], 0, result[i], 0, result[0].length);
    }
    return result;
  }

  /**
   * 深拷贝二维boolean型数组
   *
   * @param src 源数据
   * @return 深拷贝的二维boolean型数组
   */
  public static boolean[][] getClone(boolean[][] src) {
    boolean[][] result = new boolean[src.length][src[0].length];
    for (int i = 0, length = src.length; i < length; i++) {
      System.arraycopy(src[i], 0, result[i], 0, result[0].length);
    }
    return result;
  }

  /**
   * 利用Knuth洗牌算法打乱一维数组
   *
   * @param array 待打乱的数组
   */
  public static void randomizedArray(int[] array) {
    for (int i = array.length - 1, random; i >= 0; i--) {
      random = getRandomInt(0, i);
      int temp = array[i];
      array[i] = array[random];
      array[random] = temp;
    }
  }

  /**
   * 利用Knuth洗牌算法打乱二维int型数组
   *
   * @param array 待打乱的数组
   */
  public static void randomizedArray(int[][] array) {
    for (int row = array.length, column = array[0].length, random, i = row * column - 1; i >= 0; i--) {
      random = getRandomInt(0, i);
      int temp = array[i / row][i % column];
      array[i / row][i % column] = array[random / row][random % column];
      array[random / row][random % column] = temp;
    }
  }

  /**
   * 利用Knuth洗牌算法打乱二维boolean型数组
   *
   * @param array 待打乱的数组
   */
  public static void randomizedArray(boolean[][] array) {
    for (int row = array.length, column = array[0].length, random, i = row * column - 1; i >= 0; i--) {
      random = getRandomInt(0, i);
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
