package com.sudoku.model.po;

import java.io.Serializable;
import lombok.Data;

/**
 * 用户游戏信息表对应的实体类
 */
@Data
public class UserGameInformation implements Serializable {

  private static final long serialVersionUID = -3874598640462835953L;
  /**
   * 用户游戏信息的ID
   */
  private Integer id;

  /**
   * 提交的次数
   */
  private Integer total;

  /**
   * 提交正确的次数
   */
  private Integer correctNumber;

  /**
   * 平均用时
   */
  private Integer averageSpendTime;

  /**
   * 最短用时
   */
  private Integer minSpendTime;

  /**
   * 最长用时
   */
  private Integer maxSpendTime;

  /**
   * 用户ID
   */
  private Integer uid;

  /**
   * 数独等级ID
   */
  private Integer slid;
}